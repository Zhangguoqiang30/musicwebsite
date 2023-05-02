package com.zxy.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AsyncTask {

    @Async("asyncTasks")
    public void recordSetData2(RedisUtils redisUtils, String key, String value) {
        log.info("开始存储数据 = {}", "start");

        // TODO: 业务处理
        redisUtils.setEx(key, value, 20, TimeUnit.MINUTES);
        log.info("存储信息 = {}", "key2=>" + key + " " + "value2=>" + value);

        log.info("存储数据结束 = {}", "end");
    }


//    @Async("asyncTasks")
//    @Scheduled(fixedRate = 2000)
//    public void getAgvStatusFromServer() throws ServiceException {
//        try {
//            log.info("三方厂家查询60/10/5T/25TAGV查询AGV状态线程启动");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}