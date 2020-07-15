package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 写一个抛出异常的类测试
 *
 * @author: QL Zhang
 * @time: 2020/7/15 14:35
 */
@Data
@AllArgsConstructor
public class Person {
    private Long id;
    private String name;
}
