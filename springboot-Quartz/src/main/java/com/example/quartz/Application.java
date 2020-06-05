package com.example.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 本模块参考 https://github.com/527515025/springBoot 中的springboot-Quartz模块，且有对应的一篇博客。
 * https://abelyang.blog.csdn.net/article/details/86740625
 */
@SpringBootApplication
@ImportResource("classpath*:META-INF/spring/*.xml")
public class Application {
     public static void main(String[] args) {
         SpringApplication.run(Application.class, args);
     }
}
