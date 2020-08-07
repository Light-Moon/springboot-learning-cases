package com.custom.run.demo6;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;

/**
 * @description: 示例6：SpringBootApplication 注解的类实现 ApplicationRunner 接口
 *
 * 若运行此类进行启动，其他实现了ApplicationRunner接口的类中的run方法貌似并不会运行；
 * 但是，若运行Application启动类进行启动，此类中的run方法会运行，因为@SpringBootApplication注解说明此类也交给容器来管理啦。
 * @author: QL Zhang
 * @time: 2020/08/07 16:46
 **/

@Slf4j
//@SpringBootApplication
public class Demo6CustomApplicationRunner implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(Demo6CustomApplicationRunner.class, args);
        log.info("finish");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is {}", getClass().getSimpleName());
    }
}
