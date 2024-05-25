package com.exercise.boot.request;

import lombok.Data;

@Data
public class AccountRequest {
    private String account_type;
    private double balance;

    // Getters and Setters
}

