package com.exercise.boot.service;

import com.exercise.boot.entity.Account;
import com.exercise.boot.entity.Customer;
import com.exercise.boot.exception.CustomerNotFoundException;
import com.exercise.boot.mapper.CustomerMapper;
import com.exercise.boot.repository.CustomerRepository;
import com.exercise.boot.response.CustomerResponse;
import com.exercise.boot.util.AccountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private AccountUtil accountUtil;

    public Customer createCustomerWithAccounts(Customer customer) {
        logger.info("Creating customer with accounts: {}", customer);
        Random random = new Random();
        for (Account account : customer.getAccountList()) {
            long accountId = accountUtil.generateRandomAccountId(random);
            account.setAccount_id(accountId);
            logger.info("Generated account ID: {} for customer: {}", accountId, customer.getCust_id());
        }
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Customer created successfully: {}", savedCustomer);
        return savedCustomer;
    }

    @Override
    public List<Customer> createCustomerWithAccounts(List<Customer> customerList) {
        logger.info("Creating customers with accounts: {}", customerList);
        List<Customer> savedCustomers = new ArrayList<>();
        for (Customer customer : customerList) {
            Customer savedCustomer = createCustomerWithAccounts(customer);
            savedCustomers.add(savedCustomer);
        }
        logger.info("Customers created successfully: {}", savedCustomers);
        return savedCustomers;
    }

    @Override
    public CustomerResponse getCustomerByCustomerId(long customer_id) {
        logger.info("Fetching customer with customer ID: {}", customer_id);
        Optional<Customer> customerOpt = customerRepository.findById(customer_id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            CustomerResponse customerResponse = customerMapper.convertToResponse(customer);
            logger.info("Fetched customer: {}", customerResponse);
            return customerResponse;
        } else {
            logger.error("Customer not found for customer ID: {}", customer_id);
            throw new CustomerNotFoundException("Customer not found for customer ID: " + customer_id);
        }
    }

    public List<Customer> createCustomersWithAccounts(List<Customer> customers) {
        logger.info("Creating customers with accounts: {}", customers);
        List<Customer> savedCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            Customer savedCustomer = createCustomerWithAccounts(customer);
            savedCustomers.add(savedCustomer);
        }
        logger.info("Customers created successfully: {}", savedCustomers);
        return savedCustomers;
    }

    public CustomerResponse getCustomerByAccountId(long accountId) {
        logger.info("Fetching customer with account ID: {}", accountId);
        Optional<Customer> customerOpt = customerRepository.findCustomerByAccountId(accountId);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            CustomerResponse customerResponse = customerMapper.convertToResponse(customer);
            logger.info("Fetched customer: {}", customerResponse);
            return customerResponse;
        } else {
            logger.error("Customer not found for account ID: {}", accountId);
            throw new CustomerNotFoundException("Customer not found for account ID: " + accountId);
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        logger.info("Updating customer: {}", customer);
        Customer updatedCustomer = customerRepository.save(customer);
        logger.info("Customer updated successfully: {}", updatedCustomer);
        return updatedCustomer;
    }

    @Override
    public void deleteCustomer(long customerId) {
        logger.info("Deleting customer with customer ID: {}", customerId);
        customerRepository.deleteById(customerId);
        logger.info("Customer deleted successfully");
    }

    @Override
    public List<CustomerResponse> getCustomersByFirstLetterOfName(char firstLetter) {
        logger.info("Fetching customers with first letter of name: {}", firstLetter);
        List<Customer> customers = customerRepository.findByCustNameStartingWithIgnoreCase(firstLetter);
        List<CustomerResponse> customerResponses = Collections.singletonList(customerMapper.convertToResponse((Customer) customers));
        logger.info("Fetched customers: {}", customerResponses);
        return customerResponses;
    }
}