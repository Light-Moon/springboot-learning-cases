package com.swagger2word.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: QL Zhang
 * @time: 2020/08/13 11:13
 **/


@Data
public class Response implements Serializable {
    /**
     * 返回参数
     */
    private String description;

    /**
     * 参数名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
}
