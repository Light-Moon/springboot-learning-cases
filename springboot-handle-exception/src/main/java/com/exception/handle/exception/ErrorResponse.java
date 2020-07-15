package com.exception.handle.exception;

import lombok.Data;

/**
 * 1. 新建异常信息实体类,非必要的类，主要用于包装异常信息。
 *
 * @author: QL Zhang
 * @time: 2020/7/15 14:40
 */
@Data
public class ErrorResponse {

    private String message;
    private String errorTypeName;

    public ErrorResponse() {
    }

    public ErrorResponse(Exception e) {
        this(e.getClass().getName(), e.getMessage());
    }

    public ErrorResponse(String errorTypeName, String message) {
        this.errorTypeName = errorTypeName;
        this.message = message;
    }
}
