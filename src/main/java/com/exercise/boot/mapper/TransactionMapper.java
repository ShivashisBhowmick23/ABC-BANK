package com.exercise.boot.mapper;

import com.exercise.boot.entity.Transaction;
import com.exercise.boot.response.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction convertToEntity(TransactionResponse transactionResponse) {

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionResponse.getTransactionId());
        transaction.getAccount().setAccount_id(transactionResponse.getAccountId());
        transaction.setAmount(transactionResponse.getAmount());
        transaction.setTransactionType(transactionResponse.getTransactionType());
        transaction.setTransactionDate(transactionResponse.getTransactionDate());
        return transaction;
    }

    public TransactionResponse convertToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setTransactionId(transaction.getTransactionId());
        response.setAccountId(transaction.getAccount().getAccount_id());
        response.setAmount(transaction.getAmount());
        response.setTransactionType(transaction.getTransactionType());
        response.setTransactionDate(transaction.getTransactionDate());
        return response;
    }
}

