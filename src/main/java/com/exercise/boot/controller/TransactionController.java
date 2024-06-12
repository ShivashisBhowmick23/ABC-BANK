package com.exercise.boot.controller;

import com.exercise.boot.constants.BankURLConstant;
import com.exercise.boot.entity.Transaction;
import com.exercise.boot.request.TransactionRequest;
import com.exercise.boot.response.TransactionResponse;
import com.exercise.boot.service.TransactionService;
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

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.createTransaction(transactionRequest.getAccountId(), transactionRequest.getAmount(), transactionRequest.getTransactionType());
        return ResponseEntity.ok(transaction);
    }


    @GetMapping(BankURLConstant.GET_TRANSACTION_BY_ACC_ID)
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/by-date")
    public ResponseEntity<?> getTransactionsByDate(@RequestParam String date) {
        try {
            LocalDate transactionDate = LocalDate.parse(date);
            List<Transaction> transactions = transactionService.getTransactionsByDate(transactionDate);
            return ResponseEntity.ok(transactions);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Please use YYYY-MM-DD.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/by-transaction-type")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByTransactionType(@RequestParam String transactionType) {
        List<Transaction> transactions = transactionService.getTransactionsByTransactionType(transactionType);
        List<TransactionResponse> response = transactions.stream()
                .map(transaction -> {
                    TransactionResponse transactionResponse = new TransactionResponse();
                    transactionResponse.setTransactionId(transaction.getTransactionId());
                    transactionResponse.setAccountId(transaction.getAccount().getAccount_id());
                    transactionResponse.setAmount(transaction.getAmount());
                    transactionResponse.setTransactionType(transaction.getTransactionType());
                    transactionResponse.setTransactionDate(transaction.getTransactionDate());
                    return transactionResponse;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-account-id-and-transaction-type/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountIdAndTransactionType(@PathVariable Long accountId, @RequestParam String transactionType) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByAccountIdAndTransactionType(accountId, transactionType);
            List<TransactionResponse> response = transactions.stream()
                    .map(transaction -> {
                        TransactionResponse transactionResponse = new TransactionResponse();
                        transactionResponse.setTransactionId(transaction.getTransactionId());
                        transactionResponse.setAccountId(transaction.getAccount().getAccount_id());
                        transactionResponse.setAmount(transaction.getAmount());
                        transactionResponse.setTransactionType(transaction.getTransactionType());
                        transactionResponse.setTransactionDate(transaction.getTransactionDate());
                        return transactionResponse;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
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

