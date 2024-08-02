package com.exercise.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.exercise.boot.repository")
@ComponentScan(basePackages = "com.exercise.boot")
@EntityScan(basePackages = "com.exercise.boot.entity")
//@EnableDiscoveryClient -- While registering in Eureka server we need to use @EnableDiscoveryClient
public class AbcBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbcBankApplication.class, args);
    }

}
