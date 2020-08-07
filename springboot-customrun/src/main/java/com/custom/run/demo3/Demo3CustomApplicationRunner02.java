package com.custom.run.demo3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @description:
 * @author: QL Zhang
 * @time: 2020/08/07 16:08
 **/

@Slf4j
//@Component
public class Demo3CustomApplicationRunner02 implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is " + getClass().getSimpleName());
    }
}
