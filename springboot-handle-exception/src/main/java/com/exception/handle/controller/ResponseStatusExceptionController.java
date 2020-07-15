package com.exception.handle.controller;

import com.exception.handle.exception.ResourceNotFoundException;
import com.exception.handle.exception.ResourseNotFoundException2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * throwException()方法通过 ResponseStatus注解简单处理异常的方法是的好处是比较简单，但是一般我们不会这样做，
 * 通过ResponseStatusException会更加方便,可以避免我们额外的异常类。
 *
 * @author: QL Zhang
 * @time: 2020/7/15 16:43
 */
@RestController
@RequestMapping("/api")
public class ResponseStatusExceptionController {
    /**
     * 请求结果：{"timestamp":"2020-07-15T08:59:46.546+0000","status":404,"error":"Not Found","message":"Sorry, the resourse not found!","path":"/api/resourceNotFoundException2"}
     *
     * @author: QL Zhang
     * @time: 2020/7/15 16:59
     */
    @GetMapping("/resourceNotFoundException2")
    public void throwException() {
        throw new ResourseNotFoundException2("Sorry, the resourse not found!");
    }

    /**
     * 请求结果：{"timestamp":"2020-07-15T08:57:24.954+0000","status":404,"error":"Not Found","message":"No message available","path":"/api/resourceNotFoundException2/empty"}
     *
     * @author: QL Zhang
     * @time: 2020/7/15 16:59
     */
    @GetMapping("/resourceNotFoundException2/empty")
    public void throwException1() {
        throw new ResourseNotFoundException2();
    }

    @GetMapping("/resourceNotFoundException3")
    public void throwException2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, the resourse not found!", new ResourceNotFoundException());
    }
}
