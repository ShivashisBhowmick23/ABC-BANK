package com.exercise.boot.response;

import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse {
    private int cust_id;
    private String cust_name;
    private boolean verification_documents;
    private String cust_mail;
    private List<AccountResponse> accountList;

    // Getters and Setters
}

