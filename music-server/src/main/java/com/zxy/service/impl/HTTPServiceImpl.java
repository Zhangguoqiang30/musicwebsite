package com.zxy.service.impl;

import com.zxy.bean.vo.MusicConfig;
import com.zxy.bean.vo.WebBeanHelper;
import com.zxy.service.HTTPService;
import com.zxy.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求发送，接口发送，爬虫获取服务类
 */
@Service
@Slf4j
public class HTTPServiceImpl implements HTTPService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MusicConfig musicConfig;


    /**
     * 获取歌曲详情界面的url
     *
     * @param url 歌曲名称url https://www.xzmp3.com/xiazai/星辰大海.htm
     * @return
     */
    public Map<String, WebBeanHelper> getInfoUrl(String url, String sClass) {
        Map<String, WebBeanHelper> map = new LinkedHashMap<>();
        WebBeanHelper wbh = null;
        redisUtils.setEx(Constant.SONG_NAME_URL_KEY, url, 1, TimeUnit.DAYS); //存储该url 1天时间
        Document document = HTTPClient.getDocument(url);
        assert document != null;
        Elements sElements = document.select(sClass);
//        Elements elements = HTTPClient.getElements(url, sClass);
        for (int i = 0; i < sElements.size(); i += 3) {  //获取查询结果页面信息；封装到map集合中
            wbh = new WebBeanHelper();
            wbh.setMSuffix(sElements.get(i).attr(Constant.SONG_HREF));//获取到a标签的href
            wbh.setMName(sElements.get(i).text());//获取歌曲名
            wbh.setSName(sElements.get(i + 1).attr(Constant.SONG_HREF));//获取到a标签的href
            wbh.setSSuffix(sElements.get(i + 1).text());//获取歌手名
            map.put(sElements.get(i + 1).text(), wbh);
        }
//        String href = sElements.attr(Constant.SONG_HREF); //获取到a标签的href
        return map;
    }

    /**
     * 爬虫获取歌曲信息服务
     *
     * @param url 歌曲名称url
     * @return map歌曲详细详细
     */
    @Override
    public Map<String, String> getSongInfo(String url,String sName) {
        //https://www.xzmp3.com/mp3/fb8f3a845578.htm
        WebBeanHelper wbh = this.getInfoUrl(url, Constant.SONG_INFO_URL_CLASS).get(sName);
        String sUrl = "";
        if(!StringUtils.isNull(wbh)){
            sUrl = Constant.HTTPXZMP + "/" + wbh.getMSuffix();
        }else {
            try {
                throw new Exception("未查询到该歌手歌曲！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, String> map = null;
        String text = null;
        Document sDocument = HTTPClient.getDocument(sUrl);
        assert sDocument != null;
        Elements sElement = sDocument.select(Constant.SONG_NAME_URL_CLASS); //获取歌词歌曲详细信息
        redisUtils.setEx(Constant.SONG_INFO_URL_KEY, sUrl, 1, TimeUnit.DAYS); //存储该url 1天时间
        log.info("歌曲地址 = {}", sUrl);
        if (!StringUtils.isNull(sElement)) {
            map = new LinkedHashMap<>();
            int count = 0;
            for (Element element : sElement) {
                count++;
                text = element.text();
                if (text.contains(":")) {
                    String[] strArr = text.split(":");
                    map.put(strArr[0], strArr[1]);
                } else if (!text.contains(":")) {
                    map.put("歌词" + count, text);
                }
                log.info("歌曲内容 = {}", text);
            }
        }
        return map;
    }


    /**
     * @param sName 文件名称
     */
    @Async("asyncTasks") //开启多线程处理下载任务
    public void mAsyncDownload(String sName) {
        String downloadUrl = "";
        String sValue = redisUtils.get(Constant.SONG_INFO_URL_KEY);   //验证是否有数据
        if (!StringUtils.isEmpty(sValue)) {
            Document sDocument = HTTPClient.getDocument(sValue);
            assert sDocument != null;
            Elements sElements = sDocument.select(Constant.SONG_DOWNLOAD_URL_CLASS); //获取歌词歌曲详细信息
            downloadUrl = sElements.attr(Constant.SONG_HREF); //获取高速下载按钮href属性，此时获取到MP3资源位置
            if (!StringUtils.isEmpty(downloadUrl)) {
                downloadUrl = HTTPClient.sendGet(downloadUrl); //重定向之前原始的资源位置
                downloadUrl = HTTPClient.sendGet(downloadUrl); //重定向之后的MP3资源位置
            } else {
                log.info("mp3资源位置获取失败！");
            }
            try {
                HTTPClient.mDownloadUtil(downloadUrl, musicConfig, sName);
            } catch (IOException e) {
                log.info("下载出错！" + e.getMessage());
            }
            log.info(" 资源位置 = {}", downloadUrl + " 下载位置：" + musicConfig.getDownloadPath() + "/" + sName);
        } else {
            log.info(" 详情界面url= {}", "redis为存储该url");
            try {
                throw new Exception("未找到对应所需要下载的歌曲！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
