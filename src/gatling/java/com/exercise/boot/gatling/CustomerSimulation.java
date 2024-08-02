package com.exercise.boot.gatling;

import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class CustomerSimulation extends Simulation {

    private static final Logger logger = LoggerFactory.getLogger(CustomerSimulation.class);

    private static final String APPLICATION_JSON = "application/json";
    private static final String RESPONSE_BODY = "responseBody";
    private static final String CONTENT_TYPE = "Content-Type";

    FeederBuilder.FileBased<Object> customerIdsFeeder = jsonFile("simulation_request/customer_ids.json").circular();
    FeederBuilder.FileBased<Object> lettersFeeder = jsonFile("simulation_request/letters.json").circular();
    FeederBuilder.FileBased<Object> mailFeeder = jsonFile("simulation_request/new_mail.json").circular();

    // Define HTTP protocol
    HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080").acceptHeader(APPLICATION_JSON);

    // Define scenarios
    ScenarioBuilder scnGetAllCustomersLoad = scenario("Get All Customers - Load").exec(http("Get All Customers").get("/bank/customers").check(status().is(200)));

    ScenarioBuilder scnCreateSingleCustomerLoad = scenario("Create Single Customer - Load").exec(http("Create Single Customer").post("/bank/add/single-customer").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(getBody("simulation_request/create_customer.json"))).check(status().is(200)));

    ScenarioBuilder scnCreateMultipleCustomersLoad = scenario("Create Multiple Customers - Load").exec(http("Create Multiple Customers").post("/bank/add/multiple-customers").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(getBody("simulation_request/create_multiple_customers.json"))).check(status().is(200)));

    ScenarioBuilder scnGetAllCustomersStress = scenario("Get All Customers - Stress").exec(http("Get All Customers").get("/bank/customers").check(status().is(200)));

    ScenarioBuilder scnCreateSingleCustomerStress = scenario("Create Single Customer - Stress").exec(http("Create Single Customer").post("/bank/add/single-customer").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(getBody("simulation_request/create_customer.json"))).check(status().is(200)));

    ScenarioBuilder scnCreateMultipleCustomersStress = scenario("Create Multiple Customers - Stress").exec(http("Create Multiple Customers").post("/bank/add/multiple-customers").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(getBody("simulation_request/create_multiple_customers.json"))).check(status().is(200)));

    ScenarioBuilder scnGetCustomerByCustomerIdLoad = scenario("Get Customer By Customer Id - Load").feed(customerIdsFeeder).exec(http("Get Customer By Customer Id").get("/bank/customer/#{customer_id}").check(status().is(200)));

    ScenarioBuilder scnGetCustomerByCustomerIdStress = scenario("Get Customer By Customer Id - Stress").feed(customerIdsFeeder).exec(http("Get Customer By Customer Id").get("/bank/customer/#{customer_id}").check(status().is(200)));

    ScenarioBuilder scnGetCustomerByCustomerFirstLetterLoad = scenario("Get Customer By Customer First Letter - Load").feed(lettersFeeder).exec(http("Get Customer By Customer First Letter").get("/bank/customers/starts-with/#{letter}").check(status().is(200)));

    ScenarioBuilder scnGetCustomerByCustomerFirstLetterStress = scenario("Get Customer By Customer First Letter - Stress").feed(lettersFeeder).exec(http("Get Customer By Customer First Letter").get("/bank/customers/starts-with/#{letter}").check(status().is(200)));

    ScenarioBuilder scnUpdateCustomerMailLoad = scenario("Update Customer Mail - Load").feed(customerIdsFeeder).feed(mailFeeder).exec(session -> {
        String customerId = session.getString("customer_id");
        String mail = session.getString("mail");
        logger.info("Updating customer with ID: " + customerId + " to mail: " + mail);
        return session;
    }).exec(http("Update Customer Mail").put("/bank/customers/update/mail/#{customer_id}?id=#{customer_id}").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(session -> {
        String mail = session.getString("mail");
        return "{\"mail\": \"" + mail + "\"}";
    })).check(status().is(200)).check(bodyString().saveAs(RESPONSE_BODY))).exec(session -> {
        String responseBody = session.getString(RESPONSE_BODY);
        logger.info("Response: " + responseBody);
        return session;
    });

    ScenarioBuilder scnUpdateCustomerMailStress = scenario("Update Customer Mail - Stress").feed(customerIdsFeeder).feed(mailFeeder).exec(session -> {
        String customerId = session.getString("customer_id");
        String mail = session.getString("mail");
        logger.info("Updating customer with ID: " + customerId + " to mail: " + mail);
        return session;
    }).exec(http("Update Customer Mail").put("/bank/customers/update/mail/#{customer_id}?id=#{customer_id}").header(CONTENT_TYPE, APPLICATION_JSON).body(StringBody(session -> {
        String mail = session.getString("mail");
        return "{\"mail\": \"" + mail + "\"}";
    })).check(status().is(200)).check(bodyString().saveAs(RESPONSE_BODY))).exec(session -> {
        String responseBody = session.getString(RESPONSE_BODY);
        logger.info("Response: " + responseBody);
        return session;
    });


    // Consolidate Load and Stress Tests into a Single setUp
    {
        setUp(
                // Load Test Scenarios
                scnGetAllCustomersLoad.injectOpen(rampUsers(5).during(5)), scnCreateSingleCustomerLoad.injectOpen(rampUsers(5).during(5)), scnCreateMultipleCustomersLoad.injectOpen(rampUsers(5).during(5)), scnGetCustomerByCustomerIdLoad.injectOpen(rampUsers(5).during(5)), scnGetCustomerByCustomerFirstLetterLoad.injectOpen(rampUsers(5).during(5)), scnUpdateCustomerMailLoad.injectOpen(rampUsers(5).during(5)),
                // Stress Test Scenarios
                scnGetAllCustomersStress.injectOpen(atOnceUsers(50)), scnCreateSingleCustomerStress.injectOpen(atOnceUsers(50)), scnCreateMultipleCustomersStress.injectOpen(atOnceUsers(50)), scnGetCustomerByCustomerIdStress.injectOpen(atOnceUsers(50)), scnGetCustomerByCustomerFirstLetterStress.injectOpen(atOnceUsers(50)), scnUpdateCustomerMailStress.injectOpen(atOnceUsers(50))).protocols(httpProtocol).assertions(global().successfulRequests().percent().gt(75.0));
    }

    private String getBody(String route) {
        try (Stream<String> lines = Files.lines(Paths.get(Objects.requireNonNull(CustomerSimulation.class.getClassLoader().getResource(route)).toURI()))) {
            return lines.collect(Collectors.joining("\n"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
