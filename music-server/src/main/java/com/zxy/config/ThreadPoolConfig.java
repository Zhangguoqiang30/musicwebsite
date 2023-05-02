package com.zxy.config;

import com.zxy.bean.vo.ThreadpoolProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableConfigurationProperties(value = ThreadpoolProperties.class)
//@EnableAsync //开启异步请求
public class ThreadPoolConfig {

    /**
     * 创建线程池
     */
    @Bean("asyncTasks")
    public ThreadPoolTaskExecutor taskExecutor(ThreadpoolProperties properties) {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setThreadNamePrefix("------异步任务-------");
        pool.setCorePoolSize(properties.getCorePoolSize());
        pool.setMaxPoolSize(properties.getMaxPoolSize());
        pool.setKeepAliveSeconds(properties.getKeepAliveSeconds());
        pool.setQueueCapacity(properties.getQueueCapacity());
        // 指定拒绝策略
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        pool.initialize();
        return pool;
    }
}