package com.zxy.bean.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "music")
public class MusicConfig {
    private String downloadPath;
}
