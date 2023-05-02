package com.zxy.bean;

import com.zxy.bean.vo.BaseBean;
import lombok.Data;

@Data
public class MusicContent extends BaseBean {
    private Long Time; //时长 m
    private String ZuoQu;
    private String ZuoCi;
    private String ZhiZuoRen;
    private String BianQu;
    private String GeCi;
    private String XianYue;
    private String JiTa;
    private String HunYinGongChengShi;
    private String HeSheng;
    private String YiRenTongChou;
    private String TuiGuangTongChou;
    private String TeBieMingXie;
    private String LuYingPeng;

}
