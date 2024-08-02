package com.exercise.boot.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountRequest {
    private String account_type;
    private double balance;

    // Getters and Setters
}

