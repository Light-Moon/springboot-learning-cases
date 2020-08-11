package com.multi.datasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 启动类
 * 多个数据源配置使用示例，运行时注意加上参数-Djasypt.encryptor.password=";_P<70lD4m>*8j)y"
 * （本示例包含logback的简单使用配置。）
 *
 * @author: QL Zhang
 * @time: 2020/08/10 19:17
 **/

@SpringBootApplication
public class MultiDatasourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiDatasourceApplication.class, args);
    }
}
