package com.cn.lucky.morning.model.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("com.cn.lucky.morning.model")
@MapperScan("com.cn.lucky.morning.model.dao")
@EnableAsync
public class ModelWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModelWebApplication.class, args);
    }

}
