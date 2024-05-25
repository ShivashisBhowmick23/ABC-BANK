package com.exercise.boot.response;

import lombok.Data;

@Data
public class AccountResponse {
    private long account_id;
    private String account_type;
    private double balance;

    // Getters and Setters
}

