package com.exercise.boot.gatling;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class TransferSimulation extends Simulation {

    // Base URL for all requests
    private static final String BASE_URL = "http://localhost:8080/transfers";

    // HTTP Protocol configuration
    HttpProtocolBuilder httpProtocol = http.baseUrl(BASE_URL).acceptHeader("application/json").contentTypeHeader("application/json");

    // Feeder for GET requests (JSON)
    FeederBuilder<Object> getTransfersFeeder = jsonFile("simulation_request/transfers.json").circular();

    // Scenario for creating a transfer with data from JSON file
    ScenarioBuilder createTransferScenario = scenario("Create Transfer").exec(http("Create Transfer").post("/").body(StringBody(getBody("simulation_request/create_transfers.json"))) // Use getBody method
            .check(status().is(200)));

    // Scenario for fetching a transfer by ID with data from JSON file
    ScenarioBuilder getTransferByIdScenario = scenario("Get Transfer by ID").feed(getTransfersFeeder) // Feed data from JSON file
            .exec(http("Get Transfer by ID").get("/${id}") // Use data from JSON file
                    .check(status().is(200)));

    // Scenario for fetching all transfers for today
    ScenarioBuilder getAllTransfersScenario = scenario("Get All Transfers").exec(http("Get All Transfers").get("/").check(status().is(200)));

    // Scenario for fetching transfers by fromAccountId with data from JSON file
    ScenarioBuilder getTransfersByFromAccountIdScenario = scenario("Get Transfers by From Account ID").feed(getTransfersFeeder) // Feed data from JSON file
            .exec(http("Get Transfers by From Account ID").get("/fromAccount/${fromAccountId}") // Use data from JSON file
                    .check(status().is(200)));

    // Scenario for fetching transfers by toAccountId with data from JSON file
    ScenarioBuilder getTransfersByToAccountIdScenario = scenario("Get Transfers by To Account ID").feed(getTransfersFeeder) // Feed data from JSON file
            .exec(http("Get Transfers by To Account ID").get("/toAccount/${toAccountId}") // Use data from JSON file
                    .check(status().is(200)));

    // Scenario for fetching transfers by transferType with data from JSON file
    ScenarioBuilder getTransfersByTransferTypeScenario = scenario("Get Transfers by Transfer Type").feed(getTransfersFeeder) // Feed data from JSON file
            .exec(http("Get Transfers by Transfer Type").get("/type/${transferType}") // Use data from JSON file
                    .check(status().is(200)));

    // Scenario for fetching transfers by date with data from JSON file
    ScenarioBuilder getTransfersByDateScenario = scenario("Get Transfers by Date").feed(getTransfersFeeder) // Feed data from JSON file
            .exec(http("Get Transfers by Date").get("/date/${date}") // Use data from JSON file
                    .check(status().is(200)));

    // Scenario for fetching transfers between two dates (using hardcoded dates)
    ScenarioBuilder getTransfersBetweenDatesScenario = scenario("Get Transfers Between Dates").exec(http("Get Transfers Between Dates").get("/betweenDates?fromDate=2024-08-31&toDate=2024-08-30") // Hardcoded dates for example
            .check(status().is(200)));

    private String getBody(String route) {
        try (Stream<String> lines = Files.lines(Paths.get(Objects.requireNonNull(CustomerSimulation.class.getClassLoader().getResource(route)).toURI()))) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Set up the simulation
    {
        setUp(createTransferScenario.injectOpen(atOnceUsers(10)), getTransferByIdScenario.injectOpen(atOnceUsers(10)), getAllTransfersScenario.injectOpen(atOnceUsers(10)), getTransfersByFromAccountIdScenario.injectOpen(atOnceUsers(10)), getTransfersByToAccountIdScenario.injectOpen(atOnceUsers(10)), getTransfersByTransferTypeScenario.injectOpen(atOnceUsers(10)), getTransfersByDateScenario.injectOpen(atOnceUsers(10)), getTransfersBetweenDatesScenario.injectOpen(atOnceUsers(10))).protocols(httpProtocol);
    }

}
