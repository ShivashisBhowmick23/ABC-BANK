package com.exercise.boot.bdd.config;

import com.exercise.boot.request.AccountRequest;
import com.exercise.boot.request.CustomerListRequest;
import com.exercise.boot.request.CustomerRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestDataHelper {

    public CustomerRequest getValidDataCustomerRequest() {
        return CustomerRequest.builder()
                .cust_name("John")
                .verification_documents(true)
                .cust_mail("j@j.com")
                .accountList(new ArrayList<>())
                .build();
    }

    public CustomerRequest getInvalidDataCustomerRequest() {
        return CustomerRequest.builder()
                .cust_name("John")
                .verification_documents(false)
                .cust_mail("j@j.com")
                .accountList(new ArrayList<>())
                .build();
    }

    public CustomerListRequest getMultipleCustomerRequests() {
        List<CustomerRequest> customers = new ArrayList<>();
        customers.add(CustomerRequest.builder()
                .cust_name("Alice")
                .verification_documents(true)
                .cust_mail("alice@example.com")
                .accountList(new ArrayList<>())
                .build());
        customers.add(CustomerRequest.builder()
                .cust_name("Bob")
                .verification_documents(true)
                .cust_mail("bob@example.com")
                .accountList(new ArrayList<>())
                .build());
        CustomerListRequest customerListRequest = new CustomerListRequest();
        customerListRequest.setCustomers(customers);
        return customerListRequest;
    }

    public CustomerListRequest getInvalidMultipleCustomerRequests() {
        List<CustomerRequest> customers = new ArrayList<>();
        customers.add(CustomerRequest.builder()
                .cust_name("Alice")
                .verification_documents(true)
                .cust_mail("alice@example.com")
                .accountList(new ArrayList<>())
                .build());
        customers.add(CustomerRequest.builder()
                .cust_name("Bob")
                .verification_documents(false)
                .cust_mail("bob@example.com")
                .accountList(new ArrayList<>())
                .build());
        CustomerListRequest customerListRequest = new CustomerListRequest();
        customerListRequest.setCustomers(customers);
        return customerListRequest;
    }

    public CustomerRequest validRequestWithAccount() {
        return CustomerRequest.builder()
                .cust_name("John")
                .verification_documents(true)
                .cust_mail("j@j.com")
                .accountList(List.of(AccountRequest.builder().account_type("Savings").balance(1000.0).build()))
                .build();
    }
}
