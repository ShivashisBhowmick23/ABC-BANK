package com.exercise.boot.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class CustomerSimulation extends Simulation {

    // Define HTTP protocol
    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8091") // Base URL of your Spring Boot application
            .acceptHeader("application/json");
    /*This Java method getBody(String route) reads the content of a file specified by the route parameter. It uses the Files.lines() method to read all lines from the file, and then it collects these lines into a single string, using a newline character (\n) as a separator.
    The file path is obtained by calling CustomerSimulation.class.getClassLoader().getResource(route) which returns a URL object. This URL object is then converted to a Path object using Paths.get() and the toURI() method is called to get the URI of the file.
    If any error occurs during the process (like an IOException or URISyntaxException), it throws a RuntimeException with the caught exception.*/
    // Define scenarios
    ScenarioBuilder scnGetAllCustomersLoad = scenario("Get All Customers - Load").exec(http("Get All Customers").get("/bank/customers").check(status().is(200)));
    ScenarioBuilder scnCreateSingleCustomerLoad = scenario("Create Single Customer - Load").exec(http("Create Single Customer").post("/bank/add/single-customer").header("Content-Type", "application/json").body(StringBody(getBody("simulation_request/create_customer.json"))).check(status().is(200)));
    ScenarioBuilder scnCreateMultipleCustomersLoad = scenario("Create Multiple Customers - Load").exec(http("Create Multiple Customers").post("/bank/add/multiple-customers").header("Content-Type", "application/json").body(StringBody(getBody("simulation_request/create_multiple_customers.json"))).check(status().is(200)));
    ScenarioBuilder scnGetAllCustomersStress = scenario("Get All Customers - Stress").exec(http("Get All Customers").get("/bank/customers").check(status().is(200)));
    ScenarioBuilder scnCreateSingleCustomerStress = scenario("Create Single Customer - Stress").exec(http("Create Single Customer").post("/bank/add/single-customer").header("Content-Type", "application/json").body(StringBody(getBody("simulation_request/create_customer.json"))).check(status().is(200)));
    ScenarioBuilder scnCreateMultipleCustomersStress = scenario("Create Multiple Customers - Stress").exec(http("Create Multiple Customers").post("/bank/add/multiple-customers").header("Content-Type", "application/json").body(StringBody(getBody("simulation_request/create_multiple_customers.json"))).check(status().is(200)));
    ScenarioBuilder scnGetCustomerByCustomerIdLoad = scenario("Get Customer By Customer Id - Load").exec(http("Get Customer By Customer Id").get("/bank/customer/{customer_id}").check(status().is(200)));
    ScenarioBuilder scnGetCustomerByCustomerIdStress = scenario("Get Customer By Customer Id - Stress").exec(http("Get Customer By Customer Id").get("/bank/customer/{customer_id}").check(status().is(200)));
    ScenarioBuilder scnGetCustomerByCustomerFirstLetterLoad = scenario("Get Customer By Customer First Letter - Load").exec(http("Get Customer By Customer First Letter").get("/bank/customers/starts-with/{letter}").check(status().is(200)));
    ScenarioBuilder scnGetCustomerByCustomerFirstLetterStress = scenario("Get Customer By Customer First Letter - Stress").exec(http("Get Customer By Customer First Letter").get("/bank/customers/starts-with/{letter}").check(status().is(200)));

    // Consolidate Load and Stress Tests into a Single setUp
    {
        setUp(
                // Load Test Scenarios
                scnGetAllCustomersLoad.injectOpen(rampUsers(10).during(10)), scnCreateSingleCustomerLoad.injectOpen(rampUsers(10).during(10)), scnCreateMultipleCustomersLoad.injectOpen(rampUsers(10).during(10)), scnGetCustomerByCustomerIdLoad.injectOpen(rampUsers(10).during(10)), scnGetCustomerByCustomerFirstLetterLoad.injectOpen(rampUsers(10).during(10)),

                // Stress Test Scenarios
                scnGetAllCustomersStress.injectOpen(atOnceUsers(100)), scnCreateSingleCustomerStress.injectOpen(atOnceUsers(100)), scnGetCustomerByCustomerIdStress.injectOpen(atOnceUsers(100)), scnGetCustomerByCustomerFirstLetterStress.injectOpen(atOnceUsers(100)), scnCreateMultipleCustomersStress.injectOpen(atOnceUsers(100)).protocols(httpProtocol)).assertions(global().successfulRequests().percent().gt(75.0));

    }

    private String getBody(String route) {
        try {
            return Files.lines(Paths.get(Objects.requireNonNull(CustomerSimulation.class.getClassLoader().getResource(route)).toURI())).collect(Collectors.joining("\n"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

