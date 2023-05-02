package com.zxy.utils;

/**
 * 主键生成计划
 */
public class PlanKey {

    private static final String MF = "MF";
    private static final String FI = "FI";
    private static final String MU = "MU";
    private static final String ML = "ML";
    private static final String SI = "SI";
    private static final String SS = "SS";
    private static final String SO = "SO";
    private static final String SL = "SL";
    private static final String UL = "UL";

    private  String key = DateUtils.dateTimeNow();

    public  String musicFileKey() {
        return FI + this.key.substring(2);
    }

    public  String musicSingerKey() {
        return SI + this.key.substring(2);
    }

    public  String musicUserKey() {
        return MU + this.key.substring(2);
    }

    public  String musicListKey() {
        return ML + this.key.substring(2);
    }

}
