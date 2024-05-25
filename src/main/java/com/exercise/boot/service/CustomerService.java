package com.exercise.boot.service;

import com.exercise.boot.entity.Customer;
import com.exercise.boot.response.CustomerResponse;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    public Customer createCustomerWithAccounts(Customer customer);

    public CustomerResponse getCustomerByAccountId(long accountId);
}
