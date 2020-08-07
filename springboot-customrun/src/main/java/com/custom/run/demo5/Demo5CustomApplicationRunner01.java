package com.custom.run.demo5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

/**
 * @description: 示例5：使用 Ordered 接口自定义多个 ApplicationRunner 实现类的run方法执行顺序
 *
 * 代码和示例4类似。但是不用 Order 注解，而是实现 Ordered 接口的方法。
 *
 * @author: QL Zhang
 * @time: 2020/08/07 16:42
 **/

@Slf4j
//@Component
public class Demo5CustomApplicationRunner01 implements ApplicationRunner, Ordered {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is {}", getClass().getSimpleName());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
