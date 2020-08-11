package com.multi.datasource.bean;

import lombok.Data;

/**
 * @description: 用户实体类
 * @author: QL Zhang
 * @time: 2020/08/10 19:33
 **/

@Data
public class UserEntity {
    private String name;
    private String sex;
    private String addr;
    private int age;
}
