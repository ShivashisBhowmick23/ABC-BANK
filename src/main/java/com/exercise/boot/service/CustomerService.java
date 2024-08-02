package com.exercise.boot.service;

import com.exercise.boot.entity.Customer;
import com.exercise.boot.response.CustomerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    Customer createCustomerWithAccounts(Customer customer);

    List<Customer> createCustomerWithAccounts(List<Customer> customerList);

    CustomerResponse getCustomerByCustomerId(long customer_id);

    CustomerResponse getCustomerByAccountId(long accountId);

    Customer updateCustomer(Customer customer);

    void deleteCustomer(long customerId);

    List<CustomerResponse> getCustomersByFirstLetterOfName(char letter);

    List<Customer> getAllCustomers();

    String updateCustomerOnlyNameById(Long id, String name);

    String updateCustomerMailById(Long id, String mail);
}

