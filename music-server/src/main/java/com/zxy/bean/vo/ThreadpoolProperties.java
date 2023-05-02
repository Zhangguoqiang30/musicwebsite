package com.zxy.bean.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = ThreadpoolProperties.PREFIX)
public class ThreadpoolProperties {

    public static final String PREFIX = "threadpool";
    /**核心线程数*/
    private Integer corePoolSize;
    /**最大线程数*/
    private Integer maxPoolSize;
    /**队列存储数*/
    private Integer queueCapacity;
    /**最大线程存活时间*/
    private Integer keepAliveSeconds;
}