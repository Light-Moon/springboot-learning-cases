package com.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 服务器向浏览器发送的此类消息。
 */
@Data
@AllArgsConstructor
public class Response {
    private String responseMessage;
}
