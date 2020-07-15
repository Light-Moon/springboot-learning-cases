package com.example;

import com.example.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 注解方式实现AOP的demo  用的切面类是AopLogByAnnotation，配置文件是spring_aop_annotition.xml
 */
public class MainByAnnotition {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_aop_annotition.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.add();
    }
}
