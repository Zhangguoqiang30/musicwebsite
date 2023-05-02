package com.zxy.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zxy.bean.vo.BaseBean;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Data
public class User extends BaseBean {
    private String userName;

    private String userLoginName;

    private String userPassword;


    private Date startTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date singerDate;

    private MusicFile userHead;

    private List<ListBean> listBeans;
}
