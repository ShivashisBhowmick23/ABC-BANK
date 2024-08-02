package com.exercise.boot.bdd.steps;

import com.exercise.boot.bdd.config.TestDataHelper;
import com.exercise.boot.controller.CustomerController;
import com.exercise.boot.request.CustomerListRequest;
import com.exercise.boot.request.CustomerRequest;
import com.exercise.boot.response.CustomerResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerStep {

    String letter;
    @Autowired
    private CustomerController customerController;
    @Autowired
    private TestDataHelper testDataHelper;
    private CustomerRequest customerRequest;
    private CustomerListRequest customerListRequest;
    private long accountId;
    private long customerId;
    private ResponseEntity<?> response;

    @Given("the customer data is valid")
    public void theCustomerDataIsValid() {
        customerRequest = testDataHelper.getValidDataCustomerRequest();
    }

    @Given("the customer data is missing verification documents")
    public void theCustomerDataIsMissingVerificationDocuments() {
        customerRequest = testDataHelper.getInvalidDataCustomerRequest();
    }

    @When("the client requests to create a customer")
    public void theClientRequestsToCreateACustomer() {
        response = customerController.createCustomer(customerRequest);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @Then("the customer is created successfully")
    public void theCustomerIsCreatedSuccessfully() {
        assertInstanceOf(CustomerResponse.class, response.getBody());
        CustomerResponse customerResponse = (CustomerResponse) response.getBody();
        assertNotNull(customerResponse.getCust_id());
    }

    @And("the error message should be {string}")
    public void theErrorMessageShouldBe(String expectedMessage) {
        String actualMessage = (String) response.getBody();
        assertEquals(expectedMessage.replace("<accountId>", String.valueOf(accountId)), actualMessage);
    }

    @Given("the account ID exists")
    public void theAccountIDExists() {
        accountId = 234567891;
    }

    @When("the client requests the customer by account ID")
    public void theClientRequestsTheCustomerByAccountID() {
        response = customerController.getCustomerByAccountId(accountId);
    }

    @Then("the customer details by account ID are returned")
    public void theCustomerDetailsByAccountIDAreReturned() {
        assertTrue(response.getBody() instanceof CustomerResponse);
        CustomerResponse customerResponse = (CustomerResponse) response.getBody();
        assertNotNull(customerResponse);
    }

    @Given("the {long} does not exist")
    public void theAccountIDDoesNotExist(long accountId) {
        this.accountId = accountId; // Use a non-existing account ID for testing
    }

    @Given("the customer data for multiple customers is valid")
    public void theCustomerDataForMultipleCustomersIsValid() {
        customerListRequest = testDataHelper.getMultipleCustomerRequests();
    }

    @When("the client requests to create multiple customers")
    public void theClientRequestsToCreateMultipleCustomers() {
        response = customerController.addMultipleCustomers(customerListRequest);
    }

    @Then("the customers are created successfully")
    public void theCustomersAreCreatedSuccessfully() {
        assertTrue(response.getBody() instanceof List<?>);
        List<?> customerResponses = (List<?>) response.getBody();
        assertFalse(customerResponses.isEmpty());
    }

    @Given("some customer data is missing verification documents")
    public void someCustomerDataIsMissingVerificationDocuments() {
        customerListRequest = testDataHelper.getInvalidMultipleCustomerRequests();
    }

    @Given("the customerId {int} exists")
    public void theCustomerIDExists(int customerId) {
        this.customerId = customerId; // Use an existing customer ID for testing
    }

    @When("the client requests the customer by customer ID {int}")
    public void theClientRequestsTheCustomerByCustomerID(int customerId) {
        response = customerController.getCustomerByCustomerId(customerId);
    }

    @Then("the customer details by customer ID are returned")
    public void theCustomerDetailsByCustomerIDAreReturned() {
        assertTrue(response.getBody() instanceof CustomerResponse);
        CustomerResponse customerResponse = (CustomerResponse) response.getBody();
        assertNotNull(customerResponse);
    }

    @Given("the customer ID {int} does not exist")
    public void theCustomerIDDoesNotExist(int customerId) {
        this.customerId = customerId; // Use a non-existing customer ID for testing
    }

    @Given("the updated customer data is valid")
    public void theUpdatedCustomerDataIsValid() {
        customerRequest = testDataHelper.validRequestWithAccount();
    }

    @When("the client requests to update the customer by {int}")
    public void theClientRequestsToUpdateTheCustomerByCustomerID(int customerId) {
        CustomerRequest customerRequestWithAccount = testDataHelper.validRequestWithAccount();
        response = customerController.updateCustomer(customerId, customerRequestWithAccount);
    }

    @Then("the customer is updated successfully")
    public void theCustomerIsUpdatedSuccessfully() {
        assertTrue(response.getBody() instanceof CustomerResponse);
        CustomerResponse customerResponse = (CustomerResponse) response.getBody();
        assertEquals("John Doe Updated", customerResponse.getCust_name());
    }

    @When("the client requests to delete the customer by customer ID")
    public void theClientRequestsToDeleteTheCustomerByCustomerID() {
        response = customerController.deleteCustomer(customerId);
    }

    @Then("the customer is deleted successfully")
    public void theCustomerIsDeletedSuccessfully() {
        assertEquals("Customer with ID " + customerId + " deleted successfully", response.getBody());
    }

    @Given("there are customers whose names start with {string}")
    public void thereAreCustomersWhoseNamesStartWith(String letter) {
        this.letter = String.valueOf(letter.charAt(0));
    }

    @When("the client requests customers by the first letter of name {string}")
    public void theClientRequestsCustomersByTheFirstLetterOfName(String letter) {
        response = customerController.getCustomersByFirstLetterOfName(letter.charAt(0));
    }

    @And("the customer details by first letter are returned")
    public void theCustomerDetailsByFirstLetterAreReturned() {
        assertInstanceOf(List.class, response.getBody());
        List<?> customerResponses = (List<?>) response.getBody();
        assertFalse(customerResponses.isEmpty());
    }

    @When("the client requests all customers")
    public void theClientRequestsAllCustomers() {
        response = customerController.getAllCustomers();
    }

    @Then("all customer details are returned")
    public void allCustomerDetailsAreReturned() {
        assertInstanceOf(List.class, response.getBody());
        List<?> customerResponses = (List<?>) response.getBody();
        assertFalse(customerResponses.isEmpty());
    }

    @Given("the account ID {int} does not exist")
    public void theAccountIDDoesNotExist(int accountId) {
        // Assuming this account ID does not exist in the database
        this.accountId = accountId;

    }

    @When("the client requests the customer by {int}")
    public void theClientRequestsTheCustomerBy(int customerId) {
        try {
            response = customerController.getCustomerByCustomerId(customerId);
        } catch (HttpClientErrorException customerNotFoundException) {
            response = ResponseEntity.status(customerNotFoundException.getStatusCode()).body(customerNotFoundException.getResponseBodyAsString());
        }
    }

    @Given("the customer ID exists")
    public void theCustomerIDExists() {
        customerId = 10;
    }
}

