package com.exercise.boot.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerRequest {
    @NotEmpty
    private String cust_name;
    @NotEmpty
    private boolean verification_documents;
    @NotEmpty
    private String cust_mail;
    @NotEmpty
    private List<AccountRequest> accountList;

    // Getters and Setters
}

