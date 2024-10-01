package com.exercise.boot.bdd.steps;

import com.exercise.boot.controller.TransferController;
import com.exercise.boot.entity.Transfer;
import com.exercise.boot.request.TransferRequest;
import com.exercise.boot.response.TransferResponse;
import com.exercise.boot.service.TransferService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public class TransferStep {

    @Autowired
    private TransferService transferService;

    private TransferRequest transferRequest;
    private ResponseEntity<TransferResponse> responseEntity;
    private List<Transfer> transferResponses;
    private Exception exception;
    @Autowired
    private TransferController transferController;

    @Given("a transfer request with valid data")
    public void aTransferRequestWithValidData() {
        transferRequest = new TransferRequest();
        transferRequest.setFromAccountId(123456789);
        transferRequest.setToAccountId(234567891);
        transferRequest.setAmount(1000);
        transferRequest.setTransferType("BANK");
    }

    @Given("a transfer request with invalid data")
    public void aTransferRequestWithInvalidData() {
        transferRequest = new TransferRequest();
        transferRequest.setFromAccountId(-1); // Invalid data
    }

    @When("I create the transfer")
    public void iCreateTheTransfer() {
        try {
            responseEntity = transferController.createTransfer(transferRequest);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the transfer is created successfully")
    public void theTransferIsCreatedSuccessfully() {
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(transferRequest.getAmount(), responseEntity.getBody().getAmount());
    }

    @Then("the system returns an error")
    public void theSystemReturnsAnError() {
        Assertions.assertNotNull(exception);
    }

    @Given("a transfer exists with ID {int}")
    public void aTransferExistsWithID(int transferId) {
        // Assuming there's a transfer with the given ID in the database
    }

    @Given("no transfer exists with ID {int}")
    public void noTransferExistsWithID(int transferId) {
        // Assuming there's no transfer with the given ID in the database
    }

    @When("I retrieve the transfer by ID {int}")
    public void iRetrieveTheTransferByID(int transferId) {
        try {
            responseEntity = transferController.getTransferById(transferId);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the transfer details are returned")
    public void theTransferDetailsAreReturned() {
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Then("the system returns a {string} error")
    public void theSystemReturnsAnError(String errorMessage) {
        Assertions.assertNotNull(exception);
        Assertions.assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Given("transfers exist with fromAccountId {int}")
    public void transfersExistWithFromAccountId(int fromAccountId) {
        transferResponses = transferService.getTransferByFromAccountId(fromAccountId);

    }

    @When("I retrieve transfers by fromAccountId {int}")
    public void iRetrieveTransfersByFromAccountId(int fromAccountId) {
        transferResponses = transferService.getTransferByFromAccountId(fromAccountId);
    }

    @Then("the list of transfers is returned")
    public void theListOfTransfersIsReturned() {
        Assertions.assertNotNull(transferResponses);
        Assertions.assertFalse(transferResponses.isEmpty());
    }

    @Given("no transfers exist with fromAccountId {int}")
    public void noTransfersExistWithFromAccountId(int fromAccountId) {
        // Assuming there's no transfer with the given fromAccountId in the database
    }

    @When("I retrieve transfers by toAccountId {int}")
    public void iRetrieveTransfersByToAccountId(int toAccountId) {
        transferResponses = transferService.getTransferByToAccountId(toAccountId);
    }

    @Given("no transfers exist with toAccountId {int}")
    public void noTransfersExistWithToAccountId(int toAccountId) {
        // Assuming there's no transfer with the given toAccountId in the database
    }

    @Given("transfers exist with type {string}")
    public void transfersExistWithType(String transferType) {

    }

    @When("I retrieve transfers by type {string}")
    public void iRetrieveTransfersByType(String transferType) {
        transferResponses = transferService.getTransferByTransferType(transferType);
    }

    @Given("no transfers exist with type {string}")
    public void noTransfersExistWithType(String transferType) {
        // Assuming there's no transfer with the given type in the database
    }

    @Given("transfers exist on date {string}")
    public void transfersExistOnDate(String date) {
    }

    @When("I retrieve transfers by date {string}")
    public void iRetrieveTransfersByDate(String date) {
        transferResponses = transferService.getTransferByDate(LocalDate.parse(date));
    }

    @Given("transfers exist between dates {string} and {string}")
    public void transfersExistBetweenDates(String fromDate, String toDate) {
    }

    @When("I retrieve transfers between {string} and {string}")
    public void iRetrieveTransfersBetweenAnd(String fromDate, String toDate) {
        transferResponses = transferService.getTransferByToDateToFromDate(LocalDate.parse(fromDate), LocalDate.parse(toDate));
    }

    @Then("the system returns a Transfer with ID {string} not found error")
    public void theSystemReturnsATransferWithIDNotFoundError(String errorMessage) {
        Assertions.assertNotNull(exception);
        Assertions.assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Then("the system returns an empty list")
    public void theSystemReturnsAnEmptyList() {
        Assertions.assertNotNull(transferResponses);
        Assertions.assertTrue(transferResponses.isEmpty());
    }

    @Given("transfers exist with toAccountId {int}")
    public void transfersExistWithToAccountId(int transferId) {
    }
}
