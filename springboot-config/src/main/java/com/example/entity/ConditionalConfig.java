package com.example.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Spring Boot通过@ConditionalOnProperty来控制Configuration配置类是否生效
 * 通过其两个属性name以及havingValue来实现的，其中name用来从application.properties或yml文件中读取某个属性值。
 * 如果该值为空，则返回false;如果值不为空，则将该值与havingValue指定的值进行比较，如果一样则返回true;否则返回false。
 * 如果返回值为false，则该configuration不生效；为true则生效。
 *
 * @author: QL Zhang
 * @time: 2020/08/13 09:22
 **/

@Slf4j
@Configuration
//在application.properties配置"swagger.enabled"，对应的值为true
//@ConditionalOnProperty(prefix="swagger",name = "enabled", havingValue = "start")
//若无前缀则直接写成下面形式
@ConditionalOnProperty(name = "swagger", havingValue = "true")
public class ConditionalConfig {
    //通过查看运行日志是否有下面打印记录来判断是否注入到容器啦
    public ConditionalConfig() {
        log.info("构造ConditionalConfig类");
    }

    //TODO:实现相应的内容
}
