package com.custom.run.demo4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

/**
 * @description: 示例4：使用 @Order 注解自定义多个 ApplicationRunner 实现类的run方法执行顺序
 *
 * 代码和 demo03 类似，但是加上了 Order 注解。
 *
 * @author: QL Zhang
 * @time: 2020/08/07 16:39
 **/

@Slf4j
//@Component
@Order(1)  //值越小越先执行
public class Demo4CustomApplicationRunner01 implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is {}", getClass().getSimpleName());
    }
}
