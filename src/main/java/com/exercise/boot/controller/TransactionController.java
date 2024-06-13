package com.exercise.boot.controller;

import com.exercise.boot.constants.BankURLConstant;
import com.exercise.boot.entity.Transaction;
import com.exercise.boot.request.TransactionRequest;
import com.exercise.boot.response.TransactionResponse;
import com.exercise.boot.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
//@EnableDiscoveryClient while registering in Eureka server we need to use @EnableDiscoveryClient
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        logger.info("Creating transaction with accountId: {}, amount: {}, transactionType: {}", transactionRequest.getAccountId(), transactionRequest.getAmount(), transactionRequest.getTransactionType());
        Transaction transaction = transactionService.createTransaction(transactionRequest.getAccountId(), transactionRequest.getAmount(), transactionRequest.getTransactionType());
        logger.info("Transaction created: {}", transaction);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping(BankURLConstant.GET_TRANSACTION_BY_ACC_ID)
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        logger.info("Fetching transactions for account id: {}", accountId);
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        logger.info("Retrieved {} transactions for account id: {}", transactions.size(), accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/by-date")
    public ResponseEntity<?> getTransactionsByDate(@RequestParam String date) {
        logger.info("Received request to get transactions for date: {}", date);
        try {
            LocalDate transactionDate = LocalDate.parse(date);
            logger.debug("Parsed transaction date: {}", transactionDate);
            List<Transaction> transactions = transactionService.getTransactionsByDate(transactionDate);
            logger.info("Retrieved {} transactions for date: {}", transactions.size(), transactionDate);
            return ResponseEntity.ok(transactions);
        } catch (DateTimeParseException e) {
            logger.error("Error parsing date: {}", date);
            return ResponseEntity.badRequest().body("Invalid date format. Please use YYYY-MM-DD.");
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/by-transaction-type")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByTransactionType(@RequestParam String transactionType) {
        logger.info("Received request to get transactions by transaction type: {}", transactionType);

        List<Transaction> transactions = transactionService.getTransactionsByTransactionType(transactionType);
        logger.debug("Retrieved {} transactions for transaction type: {}", transactions.size(), transactionType);

        List<TransactionResponse> response = transactions.stream().map(transaction -> {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setTransactionId(transaction.getTransactionId());
            transactionResponse.setAccountId(transaction.getAccount().getAccount_id());
            transactionResponse.setAmount(transaction.getAmount());
            transactionResponse.setTransactionType(transaction.getTransactionType());
            transactionResponse.setTransactionDate(transaction.getTransactionDate());
            return transactionResponse;
        }).collect(Collectors.toList());

        logger.info("Returning {} transactions for transaction type: {}", response.size(), transactionType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-account-id-and-transaction-type/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountIdAndTransactionType(@PathVariable Long accountId, @RequestParam String transactionType) {
        try {
            logger.info("Fetching transactions for account ID: {} and transaction type: {}", accountId, transactionType);
            List<Transaction> transactions = transactionService.getTransactionsByAccountIdAndTransactionType(accountId, transactionType);
            logger.info("Found {} transactions for account ID: {}", transactions.size(), accountId);

            List<TransactionResponse> response = transactions.stream().map(transaction -> {
                TransactionResponse transactionResponse = new TransactionResponse();
                transactionResponse.setTransactionId(transaction.getTransactionId());
                transactionResponse.setAccountId(transaction.getAccount().getAccount_id());
                transactionResponse.setAmount(transaction.getAmount());
                transactionResponse.setTransactionType(transaction.getTransactionType());
                transactionResponse.setTransactionDate(transaction.getTransactionDate());
                return transactionResponse;
            }).collect(Collectors.toList());

            logger.info("Returning {} transactions for account ID: {} and transaction type: {}", response.size(), accountId, transactionType);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing transactions for account ID: {} and transaction type: {}", accountId, transactionType, e);

            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setAccountId(accountId);
            transactionResponse.setTransactionId(0L);
            transactionResponse.setAmount(0.0);
            transactionResponse.setTransactionType(transactionType);
            transactionResponse.setTransactionDate(LocalDate.now());

            // Handle the exception here, you can log the error or return an appropriate error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(transactionResponse));
        }
    }
}

