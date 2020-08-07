package com.custom.run.demo7;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: QL Zhang
 * @time: 2020/08/07 17:06
 **/

@Slf4j
@Component
@Order(1)
public class Demo7CustomApplicationRunner02 implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is {}", getClass().getSimpleName());
    }
}
