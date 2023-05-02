package com.zxy.bean;

import com.zxy.bean.vo.BaseBean;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class ManagerUser extends BaseBean {
    private String userName;
    private String userLoginName;
    private String userPassword;
    private Date singerDate; //注册日期
}
