package com.seven.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySpringApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MySpringApplication.class);
        springApplication.run(args);
        // SpringApplication.run(MySpringApplication.class, args);
    }
}
