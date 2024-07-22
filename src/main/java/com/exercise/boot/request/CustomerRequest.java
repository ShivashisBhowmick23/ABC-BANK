package com.exercise.boot.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerRequest {
    private String cust_name;
    private boolean verification_documents;
    private String cust_mail;
    private List<AccountRequest> accountList;

    // Getters and Setters
}

