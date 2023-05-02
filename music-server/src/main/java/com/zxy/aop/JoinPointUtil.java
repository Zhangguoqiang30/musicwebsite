package com.zxy.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class JoinPointUtil {

    @Pointcut("execution (* com.zxy.controller.UserController.getUserList()))")
    public void test1(){
        System.out.println("切点");
    }

    @Pointcut("execution(* com.zxy.controller.SystemController.download(..))")
    public void test2(){

    }


    @Before("test1()")
    public void toDoBeFore(JoinPoint jp){
        log.info("before");
    }

    @After("test2()")
    public void toDoAround(JoinPoint jp){
        Object[] args = jp.getArgs();
        String ms = null;
        for (Object arg : args) {
            ms+="---"+arg.toString();
        }
        if (ms!=null) {
            log.info("around" + ms.substring(0, ms.length() - 3));
        }
        log.info("around");
    }

}
