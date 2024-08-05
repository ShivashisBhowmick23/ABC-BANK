package com.exercise.boot.bdd.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/CustomerController.feature",
        glue = {"com.exercise.boot.bdd.steps", "com.exercise.boot.bdd.config"},
        plugin = {"pretty", "html:target/cucumber.html", "json:target/cucumber.json"},
        tags = "@GetCustomerByValidAccountId",
        publish = true)
public class CustomerRunner {
}
