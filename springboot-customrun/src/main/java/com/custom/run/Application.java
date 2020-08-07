package com.custom.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 自定义运行服务示例
 * 参考 https://www.letianbiji.com/spring-boot/spring-boot-applicationrunner.html
 * ApplicationRunner 接口和 CommandLineRunner 类似，在 Spring 容器启动完成时，继承 ApplicationRunner 接口的类的 run 方法会被自动执行（前提是这个类被Spring 管理也 即加上相应注解注入容器）。
 * 相比于 CommandLineRunner， ApplicationRunner 提供了对命令行参数的解析功能。
 * @author: QL Zhang
 * @time: 2020/08/07 15:15
 **/

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
