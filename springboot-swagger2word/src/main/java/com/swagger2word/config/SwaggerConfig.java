package com.swagger2word.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description: swagger配置类
 * @author: QL Zhang
 * @time: 2020/08/13 11:00
 **/

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //这里采用包含注解的方式来确定要显示的接口
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里采用包扫描的方式来确定要显示的接口
                .apis(RequestHandlerSelectors.basePackage("com.swagger2word.controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger Json Convert To Word")
                .description("Swagger接口转换成Word文档工具")
                .license("License-Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .contact(new Contact("Light-Moon", "http://light.moon.com", "1424686850@qq.com"))
                .version("1.0.0-SNAPSHOT")
                .build();
    }
}
