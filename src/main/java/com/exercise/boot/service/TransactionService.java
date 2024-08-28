package com.exercise.boot.service;

import com.exercise.boot.entity.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface TransactionService {
    Transaction createTransaction(Long accountId, double amount, String transactionType);

    List<Transaction> getTransactionsByAccountId(Long accountId);

    List<Transaction> getTransactionsByDate(LocalDate date);

    List<Transaction> getTransactionsByTransactionType(String transactionType);

    List<Transaction> getTransactionsByAccountIdAndTransactionType(Long accountId, String transactionType);

    Transaction getTransactionByTransactionId(Long transactionId);
}
