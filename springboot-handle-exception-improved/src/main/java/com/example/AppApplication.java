package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用枚举简单封装一个优雅的 Spring Boot 全局异常处理！
 * 参考：https://mp.weixin.qq.com/s/Y4Q4yWRqKG_lw0GLUsY2qw
 *
 * @author: QL Zhang
 * @time: 2020/7/15 14:23
 */ 
@SpringBootApplication
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
