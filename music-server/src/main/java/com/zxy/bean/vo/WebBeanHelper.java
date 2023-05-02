package com.zxy.bean.vo;

import lombok.Data;

@Data
public class WebBeanHelper {

    /**
     * 爬虫爬取到的网页格式：
     *
     * <a href="/mp3/fb8f3a845578.htm" class="single_line item name">星辰大海mp3下载</a>
     * <a href="/xiazai/黄霄雲.htm" class="single_line item singer">黄霄雲</a>
     * <a href="/mp3/fb8f3a845578.htm" class="item handle"><span class="handle_bt"><span>查看</span></span></a>
     *
     * <a href="/mp3/4228ba35193e.htm" class="single_line item name">星辰大海mp3下载</a>
     * <a href="/xiazai/七叔-叶泽浩.htm" class="single_line item singer">七叔-叶泽浩</a>
     * <a href="/mp3/4228ba35193e.htm" class="item handle"><span class="handle_bt"><span>查看</span></span></a>
     */
    private String mName; //歌曲名称
    private String mSuffix; //歌曲的url尾部标识
    private String sName; //歌手名称
    private String sSuffix; //歌手url尾部标识


}
