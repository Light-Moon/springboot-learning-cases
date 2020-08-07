package com.custom.run.demo7;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: QL Zhang
 * @time: 2020/08/07 17:08
 **/

@Slf4j
@Component
@Order(2)
public class Demo7CustomCommandLineRunner01 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("This is {}", getClass().getSimpleName());
    }
}
