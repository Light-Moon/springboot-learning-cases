package com.custom.run.demo2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @description: 示例2：获取命令行参数
 *
 * ApplicationRunner 与 CommandLineRunner 的区别：
 * 从log可以知道 arg= 为 CommandLineRunner 的 args数组打印时，仅仅单纯把上面的参数以空格为规则解析成了原汁原味的数组。而 ApplicationRunner 则更加精细化。（建议用实现ApplicationRunner）
 *
 * ApplicationArguments 提供了一些很有用的参数解析方法：
 * args.getOptionNames() 是获取键值对 --K=V 中的 K
 * args.getOptionValues("foo") 用来通过 K 来获取键值对的值 V
 * args.getSourceArgs() 等同于 CommandLineRunner 的 args 数组
 * args.getNonOptionArgs()  最惨用来获取单身狗
 *
 * 要注意的是 解析 ApplicationArguments 时要处理空指针问题。
 *
 * 作者：码农小胖哥
 * 链接：https://juejin.im/post/6844903986055610375
 * 来源：掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * @author: QL Zhang
 * @time: 2020/08/07 17:50
 **/

@Slf4j
@Component
public class Demo2CustomCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        for (String arg : args) {
            log.info("arg = {}", arg);
        }
    }
}
