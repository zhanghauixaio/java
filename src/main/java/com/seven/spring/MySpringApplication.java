package com.seven.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = "com.seven.spring.mapper")
@SpringBootApplication
public class MySpringApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MySpringApplication.class);
        springApplication.run(args);
        // SpringApplication.run(MySpringApplication.class, args);
    }
}
