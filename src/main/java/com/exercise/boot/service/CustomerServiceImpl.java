package com.exercise.boot.service;

import com.exercise.boot.entity.Account;
import com.exercise.boot.entity.Customer;
import com.exercise.boot.exception.CustomerNotFoundException;
import com.exercise.boot.mapper.CustomerMapper;
import com.exercise.boot.repository.CustomerRepository;
import com.exercise.boot.response.CustomerResponse;
import com.exercise.boot.util.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AccountUtil accountUtil;

    public Customer createCustomerWithAccounts(Customer customer) {
        Random random = new Random();
        for (Account account : customer.getAccountList()) {
            long accountId = accountUtil.generateRandomAccountId(random);
            account.setAccount_id(accountId);
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> createCustomerWithAccounts(List<Customer> customerList) {
        List<Customer> savedCustomers = new ArrayList<>();
        for (Customer customer : customerList) {
            Customer savedCustomer = createCustomerWithAccounts(customer);
            savedCustomers.add(savedCustomer);
        }
        return savedCustomers;
    }

    @Override
    public CustomerResponse getCustomerByCustomerId(long customer_id) {
        Optional<Customer> customerOpt = customerRepository.findById(customer_id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            // Convert customer to CustomerResponse (assuming you have a mapper for this)
            return customerMapper.convertToResponse(customer);
        } else {
            throw new CustomerNotFoundException("Customer not found for customer ID: " + customer_id);
        }
    }


    public List<Customer> createCustomersWithAccounts(List<Customer> customers) {
        List<Customer> savedCustomers = new ArrayList<>();

        for (Customer customer : customers) {
            Customer savedCustomer = createCustomerWithAccounts(customer);
            savedCustomers.add(savedCustomer);
        }

        return savedCustomers;
    }


    public CustomerResponse getCustomerByAccountId(long accountId) {
        Optional<Customer> customerOpt = customerRepository.findCustomerByAccountId(accountId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            // Convert customer to CustomerResponse (assuming you have a mapper for this)
            return customerMapper.convertToResponse(customer);
        } else {
            throw new CustomerNotFoundException("Customer not found for account ID: " + accountId);
        }
    }
}

