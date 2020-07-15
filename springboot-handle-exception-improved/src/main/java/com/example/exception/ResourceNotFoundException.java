package com.example.exception;

import java.util.Map;

/**
 * 自定义异常--资源未找到
 * 通过继承 BaseException 类我们自定义异常会变的非常简单！
 *
 * @author: QL Zhang
 * @time: 2020/7/15 14:28
 */
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(Map<String, Object> data) {
        super(ErrorCode.RESOURCE_NOT_FOUND, data);
    }
}
