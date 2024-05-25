package com.exercise.boot.controller;

import com.exercise.boot.entity.Transaction;
import com.exercise.boot.request.TransactionRequest;
import com.exercise.boot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.createTransaction(
                transactionRequest.getAccountId(),
                transactionRequest.getAmount(),
                transactionRequest.getTransactionType()
        );
        return ResponseEntity.ok(transaction);
    }


    @GetMapping("/byAccount/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }
}

