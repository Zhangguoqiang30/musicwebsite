package com.zxy.service;

import com.zxy.bean.vo.WebBeanHelper;

import java.util.Map;

public interface HTTPService {

    public Map<String,String> getSongInfo(String url,String sName);

    public Map<String, WebBeanHelper> getInfoUrl(String url, String sClass);

    public void mAsyncDownload(String sName);

}
