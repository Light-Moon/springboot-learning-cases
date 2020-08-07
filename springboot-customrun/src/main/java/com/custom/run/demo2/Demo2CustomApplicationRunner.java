package com.custom.run.demo2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * @description: 示例2：获取命令行参数  测试此示例时注意把@Component注解的注释放开
 *
 * 函数可以用来判断是否存在某个选项，可以当做开关用，也可以当做kv键值对的键来用。
 * java -jar xxx.jar  --msg
 * java -jar xxx.jar  --msg=hi test.txt
 * java -jar xxx.jar  --msg=hi --msg=hi2 test.txt
 *
 * @author: QL Zhang
 * @time: 2020/08/07 15:48
 **/

@Slf4j
//@Component
public class Demo2CustomApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("This is " + getClass().getSimpleName());
        // 获取所有命令行参数
        log.info("获取所有命令行参数");
        for (String arg: args.getSourceArgs()) {
            log.info(arg);
        }

        log.info("------");

        // 获取所有选项
        log.info("获取所有选项");
        for (String option: args.getOptionNames()) {
            log.info(option);
        }

        log.info("------");

        // 获取解析后的参数，以 --msg 为例子
        log.info("获取解析后的参数，以 --msg 为例子");
        if (args.containsOption("msg")) {
            log.info("--msg选项的值有：");
            for (String s:args.getOptionValues("msg")) {
                log.info(s);
            }
        }

        log.info("------");

        // 获取无选项指定的参数
        log.info("获取无选项指定的参数");
        for (String val: args.getNonOptionArgs()) {
            log.info(val);
        }
    }
}
