package com.demo.shedlock;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description: 启动类
 * @author: QL Zhang
 * @time: 2020/08/11 13:18
 **/

@EnableSchedulerLock(defaultLockAtMostFor = "30S")
@EnableScheduling
@SpringBootApplication
public class ShedLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShedLockApplication.class, args);
    }
}
