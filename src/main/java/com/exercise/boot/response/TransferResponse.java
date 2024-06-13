package com.exercise.boot.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransferResponse {

    private int transferId;
    private int fromAccountId;
    private int toAccountId;
    private double amount;
    private String transferType;
    private LocalDate transferDate;
}
