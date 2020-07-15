package com.example;

import com.example.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 配置方式实现AOP的demo  用的切面类是AopLogByXml，配置文件是spring_aop_xml.xml
 * 运行此demo的启动类的时候，要把切面类AopLogByAnnotation中的方法屏蔽掉。
 */
public class MainByXml {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring_aop_xml.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.add();
    }
}
