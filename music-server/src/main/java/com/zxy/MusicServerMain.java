package com.zxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zxy.**.mapper")
@EnableAsync  //开启异步调用   多线程
@EnableScheduling //开启定时任务
public class MusicServerMain {
   public static void main(String[] args) {
      SpringApplication.run(MusicServerMain.class,args);
   }
}
