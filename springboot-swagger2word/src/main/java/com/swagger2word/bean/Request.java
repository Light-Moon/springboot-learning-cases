package com.swagger2word.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: QL Zhang
 * @time: 2020/08/13 11:14
 **/
@Data
public class Request implements Serializable {
    /**
     * 参数名
     */
    private String name;

    /**
     * 数据类型
     */
    private String type;

    /**
     * 参数类型
     */
    private String paramType;

    /**
     * 是否必填
     */
    private Boolean require;

    /**
     * 说明
     */
    private String remark;

    /**
     * 复杂对象引用
     */
    private ModelAttr modelAttr;
}
