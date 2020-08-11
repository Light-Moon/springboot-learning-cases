package com.multi.datasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @description: 数据源配置类
 * 多个数据源的话类比扩展即可。前两个DataSource方法主要是给后面的JdbcTemplate方法用的。一般服务类中直接使用注入的JdbcTemplate。参见DataService示例服务类。
 * 通过将 @Qualifier 注解与我们想要使用的特定 Spring bean 的名称一起进行装配，Spring 框架就能从多个相同类型并满足装配要求的 bean 中找到我们想要的，避免让Spring脑裂。
 * @author: QL Zhang
 * @time: 2020/08/10 19:19
 **/

@Configuration
public class DataSourceConfigure {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.tsdb")
    @Qualifier("tsdbDataSource")
    DataSource tsdbDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.ctdfs")
    @Qualifier("ctdfsDataSource")
    DataSource ctdfsDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "tsdbJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("tsdbDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "ctdfsJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplateForCTDFS(@Qualifier("ctdfsDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
