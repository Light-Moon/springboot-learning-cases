package com.example.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 除了在默认的application文件进行属性配置，我们也可以自定义配置文件，例如新建 custom.properties，然后在配置类中使用@PropertySource注解注入该配置文件
 *
 */
@Configuration
@ConfigurationProperties(prefix="test")
@PropertySource("classpath:custom.properties")
@Component
@Data
public class CustomConfigBean {
	private String name;
	private int age;
	private String[] scores;
}
