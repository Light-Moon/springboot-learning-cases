package com.custom.run.demo7;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description: 示例7：ApplicationRunner 与 CommandLineRunner 混用
 *
 * 主要是想看下，Order注解能否同时对 ApplicationRunner 接口实现类和 CommandLineRunner 接口实现类，进行排序。
 * 结论：Order 注解能够同时对对ApplicationRunner 接口实现类和 CommandLineRunner 接口实现类的执行顺序排序。
 *
 * @author: QL Zhang
 * @time: 2020/08/07 17:04
 **/

@Slf4j
@Component
@Order(3)
public class Demo7CustomApplicationRunner01 implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is {}", getClass().getSimpleName());
    }
}
