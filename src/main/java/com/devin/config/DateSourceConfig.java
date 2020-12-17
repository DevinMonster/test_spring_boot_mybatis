package com.devin.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置数据源
 * @author devin
 */
@Configuration
public class DateSourceConfig {

    @Value("${devin.mysql.address}")
    private String address;
    @Value("${devin.mysql.username}")
    private String name;
    @Value("${devin.mysql.password}")
    private String password;
    @Value("${devin.mysql.driver}")
    private String driver;

    @Bean
    public DataSource setDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(address);
        dataSource.setUsername(name);
        dataSource.setPassword(password);
        return dataSource;
    }
}
