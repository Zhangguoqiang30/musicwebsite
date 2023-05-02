package com.zxy.utils;

/**
 * @title 响应消息枚举
 */
public enum HttpCode {
    // 可以根据自己的实际需要增加状态码
    SUCCESS("200", "ok"),
    SERVER_INNER_ERR("500","系统繁忙"),
    PARAM_LACK("300" , "未知错误，请联系管理员！"),
    OPERATION_FAILED("101" ,"操作失败"),
    ;

    private String code;
    private String msg;

     HttpCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
