package com.zxy.service;

import com.zxy.bean.MusicFile;
import com.zxy.bean.Singer;
import com.zxy.bean.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MySystem {
    public boolean upload2DB(MultipartFile file, MusicFile musicFile,String uploadNo);

    public boolean upload2Minio(MultipartFile file,MusicFile musicFile);

    public String resetPWD(User user);

    void InsertSinger();


}
