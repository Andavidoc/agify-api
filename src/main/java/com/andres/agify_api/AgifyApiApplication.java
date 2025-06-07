package com.andres.agify_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.andres.agify_api")
public class AgifyApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgifyApiApplication.class, args);
    }
}