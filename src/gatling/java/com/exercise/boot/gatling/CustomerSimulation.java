package com.exercise.boot.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.core.Simulation;

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

    private String getBody(String route) {
        try {
            return Files.lines(Paths.get(Objects.requireNonNull(CustomerSimulation.class.getClassLoader().getResource(route)).toURI()))
                    .collect(Collectors.joining("\n"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Define HTTP protocol
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8091") // Base URL of your Spring Boot application
            .acceptHeader("application/json");

    // Define scenarios
    ScenarioBuilder scnGetAllCustomersLoad = scenario("Get All Customers - Load")
            .exec(
                    http("Get All Customers")
                            .get("/bank/customers")
                            .check(status().is(200))
            );

    ScenarioBuilder scnCreateSingleCustomerLoad = scenario("Create Single Customer - Load")
            .exec(
                    http("Create Single Customer")
                            .post("/bank/add/single-customer")
                            .header("Content-Type", "application/json")
                            .body(StringBody(getBody("simulation_request/create_customer.json")))
                            .check(status().is(200))
            );

    ScenarioBuilder scnCreateMultipleCustomersLoad = scenario("Create Multiple Customers - Load")
            .exec(
                    http("Create Multiple Customers")
                            .post("/bank/add/multiple-customers")
                            .header("Content-Type", "application/json")
                            .body(StringBody(getBody("simulation_request/create_multiple_customers.json")))
                            .check(status().is(200))
            );

    ScenarioBuilder scnGetAllCustomersStress = scenario("Get All Customers - Stress")
            .exec(
                    http("Get All Customers")
                            .get("/bank/customers")
                            .check(status().is(200))
            );

    ScenarioBuilder scnCreateSingleCustomerStress = scenario("Create Single Customer - Stress")
            .exec(
                    http("Create Single Customer")
                            .post("/bank/add/single-customer")
                            .header("Content-Type", "application/json")
                            .body(StringBody(getBody("simulation_request/create_customer.json")))
                            .check(status().is(200))
            );

    ScenarioBuilder scnCreateMultipleCustomersStress = scenario("Create Multiple Customers - Stress")
            .exec(
                    http("Create Multiple Customers")
                            .post("/bank/add/multiple-customers")
                            .header("Content-Type", "application/json")
                            .body(StringBody(getBody("simulation_request/create_multiple_customers.json")))
                            .check(status().is(200))
            );

    // Consolidate Load and Stress Tests into a Single setUp
    {
        setUp(
                // Load Test Scenarios
                scnGetAllCustomersLoad.injectOpen(
                        rampUsers(10).during(10)
                ),
                scnCreateSingleCustomerLoad.injectOpen(
                        rampUsers(10).during(10)
                ),
                scnCreateMultipleCustomersLoad.injectOpen(
                        rampUsers(10).during(10)
                ),

                // Stress Test Scenarios
                scnGetAllCustomersStress.injectOpen(
                        atOnceUsers(100)
                ),
                scnCreateSingleCustomerStress.injectOpen(
                        atOnceUsers(100)
                ),
                scnCreateMultipleCustomersStress.injectOpen(
                        atOnceUsers(100)
                )
        ).protocols(httpProtocol)
                .assertions(
                        global().successfulRequests().percent().gt(75.0),
                        global().responseTime().mean().lt(500)
                );
    }
}
