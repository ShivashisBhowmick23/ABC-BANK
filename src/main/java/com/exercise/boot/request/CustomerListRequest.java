package com.exercise.boot.request;

import lombok.Data;

import java.util.List;

@Data
public class CustomerListRequest {
    private List<CustomerRequest> customers;
}
