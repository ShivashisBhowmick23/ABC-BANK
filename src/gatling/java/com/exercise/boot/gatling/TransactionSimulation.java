package com.exercise.boot.gatling;

import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class TransactionSimulation extends Simulation {

    private static final String APPLICATION_JSON = "application/json";
    private static final String RESPONSE_BODY = "responseBody";
    private static final String CONTENT_TYPE = "Content-Type";

    FeederBuilder.FileBased<Object> dateFeeder = jsonFile("simulation_request/dates.json").circular();


    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").acceptHeader(APPLICATION_JSON);
    ScenarioBuilder scnCreateTransactionDepositLoad = scenario("Create Transaction Deposit - Load").exec(http("Create Transaction Deposit Load").post("/transaction/create").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(getBody("simulation_request/create_transaction_deposit.json"))).check(status().is(200)));
    ScenarioBuilder scnCreateTransactionDepositStress = scenario("Create Transaction Deposit - Stress").exec(http("Create Transaction Deposit Stress").post("/transaction/create").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(getBody("simulation_request/create_transaction_deposit.json"))).check(status().is(200)));
    ScenarioBuilder scnCreateTransactionWithdrawalLoad = scenario("Create Transaction Withdrawal - Load").exec(http("Create Transaction Withdrawal Load").post("/transaction/create").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(getBody("simulation_request/create_transaction_withdrawal.json"))).check(status().is(200)));
    ScenarioBuilder scnCreateTransactionWithdrawalStress = scenario("Create Transaction Withdrawal - Stress").exec(http("Create Transaction Withdrawal Stress").post("/transaction/create").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(getBody("simulation_request/create_transaction_withdrawal.json"))).check(status().is(200)));
    ScenarioBuilder scnFetchTransactionByDateLoad = scenario("Get Transaction By Date - Load").feed(dateFeeder).exec(http("Fetch Transaction By Date").get("/transaction/by-date/#{date}").check(status().is(200)));
    ScenarioBuilder scnFetchTransactionByDateStress = scenario("Get Transaction By Date - Stress").feed(dateFeeder).exec(http("Fetch Transaction By Date").get("/transaction/by-date/#{date}").check(status().is(200)));
    {
        setUp(
                // Load Test Scenarios
                scnCreateTransactionDepositLoad.injectOpen(rampUsers(10).during(10)),
                // Stress Test Scenarios
                scnCreateTransactionDepositStress.injectOpen(atOnceUsers(100)),
                scnCreateTransactionWithdrawalLoad.injectOpen(rampUsers(10).during(10)),
                scnFetchTransactionByDateLoad.injectOpen(rampUsers(10).during(10)),
                scnFetchTransactionByDateStress.injectOpen(rampUsers(100).during(10)),
                // Stress Test Scenarios
                scnCreateTransactionWithdrawalStress.injectOpen(atOnceUsers(100))).protocols(httpProtocol).assertions(global().successfulRequests().percent().gt(75.0));
    }

    private String getBody(String route) {
        try (Stream<String> lines = Files.lines(Paths.get(Objects.requireNonNull(CustomerSimulation.class.getClassLoader().getResource(route)).toURI()))) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
