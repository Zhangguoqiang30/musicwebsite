package com.zxy.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private String code;

    private String message;

    private T data;

    public static Result getResult(){
        return new Result();
    }

    private Result(){}

    public Result(HttpCode httpCode, T date){
        this.data = date;
        this.code = httpCode.getCode();
        this.message = httpCode.getMsg();
    }

    /**
     *成功响应
     * @param httpCode状态码
     * @param date数据
     * @return result对象
     */
    public Result<T> success( T date){
        HttpCode httpCode = HttpCode.SUCCESS;
        return new Result<T>(httpCode,date);
    }

    /**
     *失败响应
     * @return
     */
    public Result<T> error(T msg){
        return new Result<T>(HttpCode.SERVER_INNER_ERR,msg);
    }


    public Result<T> toResult(){
        return new Result<T>(HttpCode.PARAM_LACK,null);
    }

}
