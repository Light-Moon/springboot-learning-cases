package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class AopLogByXml {
    // 前置通知
    public void begin() {
        System.out.println("前置通知");
    }

    //
    // 后置通知
    public void after() {
        System.out.println("后置通知");
    }

    // 运行通知
    public void afterReturning() {
        System.out.println("运行通知");
    }

    // 异常通知
    public void afterThrowing() {
        System.out.println("异常通知");
    }

    // 环绕通知
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知开始.");
        proceedingJoinPoint.proceed();
        System.out.println("环绕通知结束");
    }
}
