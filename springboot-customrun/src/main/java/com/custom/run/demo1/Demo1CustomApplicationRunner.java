package com.custom.run.demo1;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @description:  示例1：入门  测试此示例时注意把@Component注解的注释放开
 * @author: QL Zhang
 * @time: 2020/08/07 15:18
 **/

//@Component
public class Demo1CustomApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("This is " + getClass().getSimpleName());
    }
}
