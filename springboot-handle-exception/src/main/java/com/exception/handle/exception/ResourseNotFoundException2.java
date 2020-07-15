package com.exception.handle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 通过 ResponseStatus注解简单处理异常的方法（将异常映射为状态码）。
 *
 * @author: QL Zhang
 * @time: 2020/7/15 16:42
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourseNotFoundException2 extends RuntimeException {

    public ResourseNotFoundException2() {
    }

    public ResourseNotFoundException2(String message) {
        super(message);
    }
}
