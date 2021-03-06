package com.yu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: yy
 * @Date: 2020/11/19 17:35
 * @Version: 1.0.0
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //取消数据源的自动装配,使用自己的数据源
@EnableDiscoveryClient
@EnableFeignClients
public class SeataStorageMainApp2003 {
    public static void main(String[] args) {
        SpringApplication.run(SeataStorageMainApp2003.class);
    }
}