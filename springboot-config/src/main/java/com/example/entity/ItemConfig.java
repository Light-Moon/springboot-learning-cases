package com.example.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 通过@Value注解读取配置值，可以用:指定默认值
 */
@Data
@Component
public class ItemConfig {
	@Value("${mrbird.blog.name}")
	private String name;
	
	@Value("${mrbird.blog.title}")
	private String title;
	
}
