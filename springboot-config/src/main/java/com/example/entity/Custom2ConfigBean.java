package com.example.entity;

import com.example.factory.ResourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

/**
 * @PropertySource目前默认不支持 yml 文件的解析，若想实现对其的支持，通过看注解源码我们需要自定义一个工厂类 ResourceFactory，继承这个 default 工厂，重写 createPropertySource,
 * 同时在@PropertySource中指定所用工厂类即可。
 */
@Data
@ConfigurationProperties(prefix = "person")
@Configuration
@PropertySource(value = "classpath:custom2.yml", factory = ResourceFactory.class)
public class Custom2ConfigBean {
    private Map<String, String> maps1;
    private Map<String, String> maps2;
    private List<String> list1;
    private List<String> list2;
    private String name;
}
