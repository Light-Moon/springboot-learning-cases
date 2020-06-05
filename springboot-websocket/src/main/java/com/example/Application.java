package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.springframework.boot.SpringApplication.run;

/**
 * 本模块参考 https://github.com/527515025/springBoot 中的springWebSocket模块，且有对应的三篇博客。
 * 第一是 博客 spring boot ＋WebSocket 广播式（一）地址：http://blog.csdn.net/u012373815/article/details/54375195 中所示代码
 * 第二是 博客 spring boot ＋WebSocket 广播式（二）地址：http://blog.csdn.net/u012373815/article/details/54377937 中所示代码
 * 第三是 博客 spring boot ＋WebSocket（三） 点对点式 地址： http://blog.csdn.net/u012373815/article/details/54380476 中所示代码
 *
 * 启动入口
 * 启动后访问 http://localhost:8080/ws  输入admin/admin 或 user/user登录后进行操作。
 * 访问http://localhost:8080/chat用两个账号分别登录后可以进行交互聊天，这就是点对点式。
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = run(Application.class, args);
    }
}