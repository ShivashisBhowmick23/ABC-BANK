package com.exercise.boot.controller;

import com.exercise.boot.constants.BankURLConstant;
import com.exercise.boot.entity.Transaction;
import com.exercise.boot.request.TransactionRequest;
import com.exercise.boot.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BankURLConstant.TRANSACTION_SERVICE)
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(BankURLConstant.CREATE_TRANSACTION)
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.createTransaction(
                transactionRequest.getAccountId(),
                transactionRequest.getAmount(),
                transactionRequest.getTransactionType()
        );
        return ResponseEntity.ok(transaction);
    }


    @GetMapping(BankURLConstant.GET_TRANSACTION_BY_ACC_ID)
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }
}

