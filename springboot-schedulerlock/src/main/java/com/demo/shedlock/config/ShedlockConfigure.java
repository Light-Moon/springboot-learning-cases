package com.demo.shedlock.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.zookeeper.curator.ZookeeperCuratorLockProvider;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: ShedLock配置类
 * @author: QL Zhang
 * @time: 2020/08/11 13:20
 **/

@Configuration
public class ShedlockConfigure {
    @Value("${zk.url}")
    private String zkUrl;

    /**
     * 注入zk的一个客户端
     *
     * @author: QL Zhang
     * @time: 2020/8/11 13:26
     */
    @Bean
    public CuratorFramework getCuratorFramework(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkUrl,retryPolicy);
        client.start();
        return client;
    }

    /**
     * 参数是zk上节点对应的路径，可以自定义
     *
     * @author: QL Zhang
     * @time: 2020/8/11 13:26
     */
    @Bean
    public LockProvider lockProvider(CuratorFramework client) {
        return new ZookeeperCuratorLockProvider(client ,"/springboot_demo_shedlock");
    }
}
