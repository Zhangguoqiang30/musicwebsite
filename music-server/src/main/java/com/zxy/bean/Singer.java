package com.zxy.bean;

import com.zxy.bean.vo.BaseBean;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
public class Singer extends BaseBean {
    private String singerName;
    private int singerAge;
    private int singerSex;
    private String singerAligns;
    private String singerBirthday;
    private String singerNation;
    private String head;
    private String singerRemake;
    private Date singerDate;
}
