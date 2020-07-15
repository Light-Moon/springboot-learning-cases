package com.exception.handle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot 处理异常的几种常见姿势
 * 参考：https://mp.weixin.qq.com/s/2grV4lolzv_1twKzu6eGEQ
 *
 * (一)使用 @ControllerAdvice 和 @ExceptionHandler 处理全局异常  （直接用这种就好啦）
 * 目前很常用的一种方式，非常推荐。测试代码中用到了 Junit 5，如果你新建项目验证下面的代码的话，记得添加上相关依赖。步骤如下：
 * 1. 新建异常信息实体类 ErrorResponse
 * 2. 自定义异常类型 ResourceNotFoundException
 * 3. 新建异常处理类 GlobalExceptionHandler
 * 4. controller模拟抛出异常 ExceptionController
 * 5. 编写测试类 ExceptionTest
 *
 * （二）ResponseStatusException
 * 研究 ResponseStatusException前，可以先来看看通过 ResponseStatus注解简单处理异常的方法（将异常映射为状态码）。
 * 1. 自定义异常类型 ResourceNotFoundException2
 * 2. controller模拟抛出异常 ResponseStatusExceptionController
 *
 *
 * @author: QL Zhang
 * @time: 2020/7/15 14:40
 */
@SpringBootApplication
public class AppApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
