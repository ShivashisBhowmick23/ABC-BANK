package com.exercise.boot.bdd.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = {"com.exercise.boot.bdd.steps", "com.exercise.boot.bdd.config"},
        plugin = {"pretty", "html:build/reports/cucumber-reports/cucumber.html", "json:build/reports/cucumber-reports/cucumber.json"},
        publish = true)
public class AcceptanceRunner {
}
