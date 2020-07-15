package com.example.exception;

import com.example.web.ExceptionController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常捕获。使用 @ControllerAdvice 和 @ExceptionHandler 处理全局异常,这是目前很常用的一种方式，非常推荐!
 *
 * 类中定义了两个异常捕获方法，实际上这个类只需要 handleAppException() 这一个方法就够了，因为它处理的是本系统所有异常的父类。只要是抛出了继承 BaseException 类的异常后都会在这里被处理。
 * 不过，当我们抛出了 ResourceNotFoundException异常，它是会被handleResourceNotFoundException方法捕获处理，因为 @ExceptionHandler 捕获异常的过程中，会优先找到最匹配的。
 *
 * @author: QL Zhang
 * @time: 2020/7/15 14:29
 */
@ControllerAdvice(assignableTypes = {ExceptionController.class})
@ResponseBody
public class GlobalExceptionHandler {
    //拦截BaseException及其子类所有异常, 一般情况下一个方法特定处理一种异常
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleAppException(BaseException ex, HttpServletRequest request) {
        ErrorReponse representation = new ErrorReponse(ex, request.getRequestURI());
        return new ResponseEntity<>(representation, new HttpHeaders(), ex.getError().getStatus());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorReponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorReponse errorReponse = new ErrorReponse(ex, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorReponse);
    }
}
