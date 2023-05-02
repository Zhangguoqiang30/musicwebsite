package com.zxy.config;

import com.zxy.bean.vo.MinioConfig;
import com.zxy.bean.vo.MusicConfig;
import com.zxy.utils.FileUtil;
import com.zxy.utils.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * ApplicationRunner实现此接口重写run（）方法初始化项目配置
 */
@Component
@Order(3)
@Slf4j
public class Application2Runner implements ApplicationRunner {

    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MusicConfig musicConfig;

    @Override
    public void run(ApplicationArguments args) {
        init();
        System.out.println(Application2Runner.run());

    }

    public static String run() {
        return "startup success!";
    }

    public void init() {
        try {
        //创建存储痛
        minioUtils.createBucket(minioConfig.getFileBucketName());
        }catch (Exception e){
            new Exception("存储痛创建失败！");
        }
        if (0 == FileUtil.fileExists(musicConfig.getDownloadPath())) {
            File file = new File(musicConfig.getDownloadPath());
            if (file.mkdirs()) {
                log.info("目录已成功创建:" + musicConfig.getDownloadPath() );
            }
        } else {
            log.info("目录已存在:" + musicConfig.getDownloadPath());
        }
    }

}