package com.exception.handle.exception;

import com.exception.handle.controller.ExceptionController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 新建异常处理类
 * 我们只需要在类上加上@ControllerAdvice注解这个类就成为了全局异常处理类，当然你也可以通过 assignableTypes指定特定的 Controller 类，让异常处理类只处理特定类抛出的异常。
 *
 * @author: QL Zhang
 * @time: 2020/7/15 14:42
 */
@ControllerAdvice(assignableTypes = {ExceptionController.class})
@ResponseBody
public class GlobalExceptionHandler {

    ErrorResponse illegalArgumentResponse = new ErrorResponse(new IllegalArgumentException("参数错误!"));
    ErrorResponse resourseNotFoundResponse = new ErrorResponse(new ResourceNotFoundException("Sorry, the resourse not found!"));
    ErrorResponse resourseNotFoundResponse2 = new ErrorResponse(new ResourseNotFoundException2("Sorry, ResourseNotFoundException2 happen..."));

    // 拦截所有异常
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {

        if (e instanceof IllegalArgumentException) {
            return ResponseEntity.status(400).body(illegalArgumentResponse);
        } else if (e instanceof ResourceNotFoundException) {
            return ResponseEntity.status(404).body(resourseNotFoundResponse);
        }
        //加上下面也不会在此全局异常这里进行处理，因为ResourseNotFoundException2异常通过注解@ResponseStatus(code = HttpStatus.NOT_FOUND)进行处理啦
        //}else if (e instanceof  ResourseNotFoundException2){
        //    return ResponseEntity.status(404).body(resourseNotFoundResponse2);
        //}
        return null;
    }
}
