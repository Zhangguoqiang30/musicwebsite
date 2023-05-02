package com.zxy.bean.vo;

import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@Component
public class BaseBean {
    private String primaryKey;
    private String publicStr; //公共字段

    private int pageCode;
    private int pageSize;
}
