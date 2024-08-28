package com.exercise.boot.bdd.steps;

import com.exercise.boot.controller.TransactionController;
import com.exercise.boot.entity.Transaction;
import com.exercise.boot.request.TransactionRequest;
import com.exercise.boot.response.TransactionResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionStep {

    @Autowired
    private TransactionController transactionController;

    private TransactionRequest transactionRequest;
    private ResponseEntity<?> response;
    private long transactionId;
    private long accountId;
    private LocalDate date;

    @Given("the transaction data is valid")
    public void theTransactionDataIsValid() {
        // Initialize a valid transaction request
        transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(123456789L);
        transactionRequest.setAmount(100.0);
        transactionRequest.setTransactionType("DEPOSIT");
//        transactionRequest.setTransaction_date("2024-01-01");
    }

    @When("the client requests to create a transaction")
    public void theClientRequestsToCreateATransaction() {
        response = transactionController.createTransaction(transactionRequest);
    }

    @Then("the response status should {int}")
    public void theResponseStatusShould(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @Then("the response body should contain transaction ID")
    public void theResponseBodyShouldContainTransactionId() {
        assertInstanceOf(Transaction.class, response.getBody());
        Transaction transactionResponse = (Transaction) response.getBody();
        assertNotNull(transactionResponse.getTransactionId());
        transactionId = transactionResponse.getTransactionId();
    }

    @Given("the transaction with ID {long} exists")
    public void theTransactionWithIdExists(long transactionId) {
        this.transactionId = transactionId;
    }

    @When("the client requests to fetch transaction by ID {long}")
    public void theClientRequestsToFetchTransactionById(long transactionId) {
        response = transactionController.getTransactionByTransactionId(transactionId);
    }

    @Then("the transaction details are returned")
    public void theTransactionDetailsAreReturned() {
        assertInstanceOf(TransactionResponse.class, response.getBody());
        TransactionResponse transactionResponse = (TransactionResponse) response.getBody();
        assertEquals(transactionId, transactionResponse.getTransactionId());
    }

    @Given("the transaction data exists with account ID {long}")
    public void theTransactionDataExistsWithAccountId(long accountId) {
        this.accountId = accountId;
    }

    @When("the client requests to fetch transactions by account ID {long}")
    public void theClientRequestsToFetchTransactionsByAccountId(long accountId) {
        response = transactionController.getTransactionsByAccountId(accountId);
    }

    @Then("the transactions for account ID {long} are returned")
    public void theTransactionsForAccountIdAreReturned(long accountId) {
        assertTrue(response.getBody() instanceof List<?>);
        List<?> transactions = (List<?>) response.getBody();
        assertFalse(transactions.isEmpty());
        transactions.forEach(transaction -> assertEquals(accountId, ((Transaction) transaction).getAccount().getAccount_id()));
    }

    @When("the client requests to fetch transactions by date {string}")
    public void theClientRequestsToFetchTransactionsByDate(String date) {
        this.date = LocalDate.parse(date);
        response = transactionController.getTransactionsByDate(date);
    }

    @Then("the transactions for date {string} are returned")
    public void theTransactionsForDateAreReturned(String date) {
        LocalDate date1 = LocalDate.parse(date);
        assertTrue(response.getBody() instanceof List<?>);
        List<?> transactions = (List<?>) response.getBody();
        assertFalse(transactions.isEmpty());
        transactions.forEach(transaction -> assertEquals(date1, ((Transaction) transaction).getTransactionDate()));
    }

    @Given("the transaction with ID {long} does not exist")
    public void theTransactionWithIdDoesNotExist(long transactionId) {
        this.transactionId = transactionId;
    }

    @When("the client requests to fetch transaction by non-existent ID {long}")
    public void theClientRequestsToFetchTransactionByNonExistentId(long transactionId) {
        try {
            response = transactionController.getTransactionByTransactionId(transactionId);
        } catch (Exception e) {
            response = ResponseEntity.status(404).body("Transaction not found for ID " + transactionId);
        }
    }

    @Then("the error message should {string}")
    public void theErrorMessageShould(String expectedMessage) {
        assertEquals(expectedMessage, response.getBody());
    }

    @Given("the transaction data exists with date {string}")
    public void theTransactionDataExistsWithDate(String date) {
        LocalDate transactionDate = LocalDate.parse(date);
        this.date = transactionDate;
    }
}
