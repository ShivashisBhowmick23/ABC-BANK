package com.exercise.boot.bdd.steps;

import com.exercise.boot.controller.TransactionController;
import com.exercise.boot.entity.Transaction;
import com.exercise.boot.request.TransactionRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

public class TransactionStep {
    @Autowired
    private TransactionController transactionController;
    private ResponseEntity<Transaction> response;
    private TransactionRequest transactionRequest;


    @Given("transaction request with accountId {long}, amount {double}, and transactionType {string}")
    public void transaction_request_with_accountId_amount_and_transactionType(Long accountId, double amount, String transactionType) {
        transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(accountId);
        transactionRequest.setAmount(amount);
        transactionRequest.setTransactionType(transactionType);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), response.getStatusCode());
    }

    @Then("the response should contain a transaction with accountId {long}, amount {double}, and transactionType {string}")
    public void the_response_should_contain_a_transaction_with_accountId_amount_and_transactionType(Long accountId, double amount, String transactionType) {
        Transaction transaction = response.getBody();
        assertEquals(accountId, transaction.getAccount().getAccount_id()); // corrected to match Account entity field name
        assertEquals(amount, transaction.getAmount(), 0.001); // tolerance for floating-point comparison
        assertEquals(transactionType, transaction.getTransactionType());
    }

    @When("the client requests to create a transaction")
    public void theClientRequestsToCreateATransaction() {
        response = transactionController.createTransaction(transactionRequest);
    }
}
