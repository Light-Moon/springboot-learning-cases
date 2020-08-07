package com.custom.run.demo3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @description: 示例3：多个 ApplicationRunner 实现类
 * @author: QL Zhang
 * @time: 2020/08/07 16:05
 **/

//@Component
@Slf4j
public class Demo3CustomApplicationRunner01 implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is " + getClass().getSimpleName());
    }
}
