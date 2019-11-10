package com.cn.lucky.morning.model.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cn.lucky.morning.model")
public class ModelWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModelWebApplication.class, args);
    }

}
