package com.exercise.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient -- While registering in Eureka server we need to use @EnableDiscoveryClient
public class AbcBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbcBankApplication.class, args);
    }

}
