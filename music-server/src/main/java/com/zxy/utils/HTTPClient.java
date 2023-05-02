package com.zxy.utils;

import com.zxy.bean.vo.MusicConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;


/**
 * http接口请求工具类
 */
@Slf4j
public class HTTPClient {

    /**
     * 发送get请求
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        String result = "";
        URL realUrl = null;
        URLConnection connection = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            realUrl = new URL(url);
            // 打开和URL之间的连接
            connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            client = HttpClients.createDefault();
            final HttpGet request = new HttpGet(url);
            HttpParams params = new BasicHttpParams();
            params.setParameter("http.protocol.handle-redirects", false); // 默认不让重定向 这样才可以拿到location
            request.setParams(params);
            response = client.execute(request);
            connection.connect();
            // 获取所有响应头字段
            //connection.getHeaderFields();//获取响应头  key - value形式
            Header header = response.getFirstHeader("Location"); //获取location地址
            result = header.getValue();
        } catch (Exception e) {
            log.info("发送get请求失败 ！={}" + e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (client != null) {
                    client.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e2) {
                log.info("关闭流失败 ！={}" + e2.getMessage());
            }
        }
        return result;
    }


    /**
     * POST接口请求
     *
     * @param url  地址
     * @param json json字符串
     * @return
     * @throws Exception
     */
    public static String sendPost(String url, String json) {
        String data = "";
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(url);
            httppost.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(json, Charset.forName("UTF-8"));
            se.setContentType("text/json");
            se.setContentEncoding("UTF-8");
            httppost.setEntity(se);
            response = httpClient.execute(httppost);
            int code = response.getStatusLine().getStatusCode();
            System.out.println("接口响应码:" + code);
            data = EntityUtils.toString(response.getEntity(), "utf-8");
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                }
            }
        }
        return data;
    }


    /**
     * 获取Document上下文对象
     *
     * @param url url地址
     * @return
     */
    public static Document getDocument(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8")
//                    .header("Cookie", "ntes_nnid=6c5b87bd25a17a9fd9692580e5c94f78,1565912650142; _ntes_nuid=6c5b87bd25a17a9fd9692580e5c94f78; _iuqxldmzr_=32; WM_TID=LDdg6Rcj9ENEBRFUUFc4pPF4%2B6vTAn2G; WM_NI=R9FV8%2B3KZYFzFTyT7isTQivbb2VLf%2FzcQWAi%2BQdwZbxir0FYRR17q5zGEaYaTxwuyNrXXwr8kuNyRC2wcdeCeCAMWeyd1e8YJR%2FyJPg1kc3dMwiiFWuGVyQtxssnI3kBT04%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6eed1cf4eacb8bb8ac641899e8fb3c85b928f8e84f333a7939790c14f8a90fdd3d92af0fea7c3b92a92e78ab7bb21f19596bac572868cb7b5b663b18c9f8af56aa2ee9aade525babd9fb8c463e98fac98d96abcb7bed1c553928effd8f43fba998b82dc6b98a8b996fc46b2889898f134a9ab829ad149f2a9ad85e849a79d8faed66fbbbcff86bb538a8ee19ac95ca5efa584b2708fa9a78ac55db7999a9ad480bcadbd8fcc39a99e9cd1b737e2a3; JSESSIONID-WYYY=yBXBK%2FIFCVHGtcBTi3%5CSUeDQMvfzApFAMBZzlZ%2BENNt7n2f9j2SCTvBRQpFACIc5EnGK3%2BtFhTQJWOhtCkJvHZ8olJ83RYG8Exukhj6Ftzw%2FBwylje03bjPW4Vl9IXXOHeNIRWxO4%2BKndGOJ0HjhnNZJtoESJht8PfF%2FfzAVXh6kOWiq%3A1566909226292")
//                    .header("Referer", "https://music.163.com/discover/artist/cat?id=1001&initial=65")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("Accept", "application/json, text/plain, */*")
                    .header("Accept-Encoding", "Accept-Encoding")
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .method(Connection.Method.GET)
                    .timeout(200000).get(); // 设置请求头等信息，模拟人工访问，超时时间可自行设置
        } catch (IOException e) {
            try {
                throw new Exception("获取Document上下文对象失败！");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (!StringUtils.isNull(document)) {
            return document;
        }
        return null;
    }

    /**
     * 根据url下载资源
     *
     * @param sUrl        资源位置
     * @param musicConfig 下载路径
     * @param sName       文件名称
     */
    public static void mDownloadUtil(String sUrl, MusicConfig musicConfig, String sName) throws IOException {
        URL url = new URL(sUrl);
        //2.连接到这个资源 HTTP
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream fos = new FileOutputStream(musicConfig.getDownloadPath() + "/" + sName);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);//写出这个数据
        }
        fos.close();
        inputStream.close();
        urlConnection.disconnect();//断开连接
    }

}
