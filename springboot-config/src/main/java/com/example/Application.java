package com.example;

import com.example.entity.BeanConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @EnableConfigurationProperties 注解的作用是：使使用 @ConfigurationProperties 注解的类生效。
 * 如果一个配置类只配置@ConfigurationProperties注解，而没有使用@Component，那么在IOC容器中是获取不到properties 配置文件转化的bean。
 * 说白了 @EnableConfigurationProperties 相当于把使用 @ConfigurationProperties 的类进行了一次注入。
 * 不过实际中直接在@ConfigurationProperties注解的类上加@Configuration或@Component就行啦。
 *
 *  扩展：@ImportResource：该注解导入Spring的xml配置文件，让配置文件里面的内容生效。
 *  例如：@ImportResource(locations = {"classpath:beans.xml"})
 *
 */
@SpringBootApplication
@EnableConfigurationProperties({BeanConfig.class})
//@ImportResource({"classpath:some-application.xml"})
public class Application {
	
	public static void main(String[] args) {
		//一般启动类写法
		//SpringApplication.run(Application.class, args);
		SpringApplication app = new SpringApplication(Application.class);
		// 正常情况可以通过命令行启动参数来修改springboot应用的一些属性值，下面语句是禁用命令行参数的作用
		app.setAddCommandLineProperties(false);
		app.run(args);
	}
}
