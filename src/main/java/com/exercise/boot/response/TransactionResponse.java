package com.exercise.boot.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionResponse {

    private Long transactionId;
    private Long accountId;
    private double amount;
    private String transactionType;
    private LocalDate transactionDate;
}
