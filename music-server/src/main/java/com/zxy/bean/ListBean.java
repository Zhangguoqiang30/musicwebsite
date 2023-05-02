package com.zxy.bean;

import com.zxy.bean.vo.BaseBean;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class ListBean extends BaseBean {
    private String listName;
    private Date listCreatDate;
    private String listRemark;
}
