package com.exercise.boot.bdd.config;

import com.exercise.boot.AbcBankApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = AbcBankApplication.class)
public class CucumberSpringConfiguration {
}
