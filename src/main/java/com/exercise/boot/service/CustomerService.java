package com.exercise.boot.service;

import com.exercise.boot.entity.Customer;
import com.exercise.boot.response.CustomerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    public Customer createCustomerWithAccounts(Customer customer);

    public List<Customer> createCustomerWithAccounts(List<Customer> customerList);

    public CustomerResponse getCustomerByCustomerId(long customer_id);

    public CustomerResponse getCustomerByAccountId(long accountId);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(long customerId);

    List<CustomerResponse> getCustomersByFirstLetterOfName(char letter);

    List<Customer> getAllCustomers();
}

