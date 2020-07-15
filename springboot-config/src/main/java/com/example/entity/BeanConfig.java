package com.example.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ConfigurationProperties 注解向Spring Boot声明该类中的所有属性和配置文件中相关的配置进行绑定。
 * @Component 或者@Configuration：将该组件加入Spring Boot容器，只有这个组件是容器中的组件，配置才生效。
 * 若被@ConfigurationProperties注解的类没有被@Component 或者@Configuration注解，则要在启动类中加上
 * @EnableConfigurationProperties({BeanConfig.class, CustomConfigBean.class})，括号里面是相关类。
 */
@Data
@ConfigurationProperties(prefix="mrbird.blog")
public class BeanConfig {
	private String name;
	private String title;
	private String wholeTitle;
}
