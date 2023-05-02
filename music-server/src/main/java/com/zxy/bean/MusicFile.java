package com.zxy.bean;

import com.zxy.bean.vo.BaseBean;
import lombok.Data;

import java.util.Date;

@Data
public class MusicFile extends BaseBean {
    private String fileName;
    private int fileType;
    private String fileUrl;
    private Date fileDate;

}
