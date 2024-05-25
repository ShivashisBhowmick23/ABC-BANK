package com.exercise.boot.service;

import com.exercise.boot.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    public Transaction createTransaction(Long accountId, double amount, String transactionType);

    public List<Transaction> getTransactionsByAccountId(Long accountId);
}
