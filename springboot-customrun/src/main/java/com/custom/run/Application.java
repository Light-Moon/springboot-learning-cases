package com.custom.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 参考 https://www.letianbiji.com/spring-boot/spring-boot-applicationrunner.html
 *
 * @description: Springboot启动时执行指定代码也即运行自定义服务示例
 * 每个demo包下可以理解成一个示例子项目，运行时注意屏蔽掉其他demo包中的@Component注解也即将类不让spring容器来管理也就不执行啦
 *
 * ApplicationRunner 接口和 CommandLineRunner 类似，在 Spring 容器启动完成时，继承 ApplicationRunner 接口的类的 run 方法会被自动执行（前提是这个类被Spring 管理也 即加上相应注解注入容器）。
 * 启动Spring应用后，如果还想进一步执行其他代码，可以定义Spring bean类以实现CommandLineRunner接口或ApplicationRunner接口，把需要执行的代码逻辑等重写进run方法内。
 *
 * CommandLineRunner接口能够以String数组的形式访问Spring应用的启动参数args，获取参数时比较单纯；
 * ApplicationRunner接口能够以ApplicationArguments的形式访问应用的参数args，获取参数时更加精细，推荐。
 * @author: QL Zhang
 * @time: 2020/08/07 15:15
 **/

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        //禁用LOGO
        //SpringApplication app = new SpringApplication(Application.class);
        //app.setBannerMode(Banner.Mode.OFF);
        //app.run(args);
    }
}
