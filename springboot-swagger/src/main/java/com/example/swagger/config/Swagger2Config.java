package com.example.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"dev", "test"})// 设置 dev test 环境开启
public class Swagger2Config {

    /**
     * 方式一
     *
     * @author: QL Zhang
     * @time: 2020/8/12 15:49
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //这里采用包含注解的方式来确定要显示的接口
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //这里采用包扫描的方式来确定要显示的接口
                .apis(RequestHandlerSelectors.basePackage("com.example.swagger.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("spring-boot-demo")
                .description("这是一个简单的 Swagger API 演示")
                .license("License-Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .contact(new Contact("Light-Moon", "http://light.moon.com", "1424686850@qq.com"))
                .version("1.0.0-SNAPSHOT")
                .build();
    }

    /**
     * 方式二：亦可以直接用下面一个方法
     *
     * @author: QL Zhang
     * @time: 2020/8/12 15:48
     */
    //@Bean
    //public Docket createRestApi() {
    //    return new Docket(DocumentationType.SWAGGER_2)
    //            .pathMapping("/")
    //            .select()
    //            .apis(RequestHandlerSelectors.basePackage("com.example.swagger.controller"))
    //            .paths(PathSelectors.any())
    //            .build().apiInfo(new ApiInfoBuilder()
    //                    .title("SpringBoot整合Swagger")
    //                    .description("SpringBoot整合Swagger，详细信息......")
    //                    .version("9.0")
    //                    .contact(new Contact("啊啊啊啊","blog.csdn.net","aaa@gmail.com"))
    //                    .license("The Apache License")
    //                    .licenseUrl("http://www.baidu.com")
    //                    .build());
    //}


}
