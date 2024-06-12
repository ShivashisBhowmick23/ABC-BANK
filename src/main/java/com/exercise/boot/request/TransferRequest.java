package com.exercise.boot.request;

import lombok.Data;

@Data
public class TransferRequest {
    private int fromAccountId;
    private int toAccountId;
    private double amount;
    private String transferType;
}
