package com.exercise.boot.request;

import lombok.Data;

@Data
public class TransactionRequest {
    private Long accountId;
    private double amount;
    private String transactionType;
}
