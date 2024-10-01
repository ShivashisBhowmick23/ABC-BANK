package com.exercise.boot.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotEmpty
    private String cust_name;
    @NotNull
    private Boolean verification_documents;
    @NotEmpty
    private String cust_mail;
    @NotEmpty
    private List<AccountRequest> accountList;

    // Getters and Setters
}

