package com.exercise.boot.service;

import com.exercise.boot.entity.Account;
import com.exercise.boot.entity.Customer;
import com.exercise.boot.exception.CustomerNotFoundException;
import com.exercise.boot.mapper.CustomerMapper;
import com.exercise.boot.repository.AccountRepository;
import com.exercise.boot.repository.CustomerRepository;
import com.exercise.boot.response.AccountResponse;
import com.exercise.boot.response.CustomerResponse;
import com.exercise.boot.util.AccountUtil;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountUtil accountUtil;

    public Customer createCustomerWithAccounts(Customer customer) {
        logger.info("Creating customer with accounts: {}", customer);
        Random random = new Random();
        for (Account account : customer.getAccountList()) {
            long accountId = accountUtil.generateRandomAccountId(random);
            account.setAccount_id(accountId);
            accountRepository.save(account);
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

    @Transactional
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
            String errorMessage = "Customer not found for customer ID: " + customer_id;
            logger.error(errorMessage);
            throw new CustomerNotFoundException(errorMessage);
        }
    }


    public List<Customer> createCustomersWithAccounts(List<Customer> customers) {
        logger.info("Creating customers with accounts : {}", customers);
        List<Customer> savedCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            Customer savedCustomer = createCustomerWithAccounts(customer);
            savedCustomers.add(savedCustomer);
        }
        logger.info("Customers created successfully : {}", savedCustomers);
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
        Customer updatedCustomer = customerRepository.saveAndFlush(customer);
        logger.info("Customer updated successfully: {}", updatedCustomer);
        return updatedCustomer;
    }

    @Override
    public void deleteCustomer(long customerId) {
        try {
            logger.info("Deleting customer with customer ID: {}", customerId);
            customerRepository.deleteById(customerId);
            logger.info("Customer deleted successfully");
        } catch (Exception e) {
            logger.error("An error occurred while deleting customer with customer ID: {}", customerId, e);
        }
    }

    @Override
    public List<CustomerResponse> getCustomersByFirstLetterOfName(char letter) {
        List<Customer> customers = customerRepository.findByCustNameStartingWithIgnoreCase(letter);
        return customers.stream()
                .map(this::mapToCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public String updateCustomerOnlyNameById(Long id, String name) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setCust_name(name);
            customerRepository.save(customer);
            return "Customer updated successfully";
        } else {
            return "Customer not found";
        }
    }

    @Override
    public String updateCustomerMailById(Long id, String mail) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setCust_mail(mail);
            customerRepository.save(customer);
            return "Customer mail_id updated successfully";
        } else {
            return "Customer not found";
        }
    }

    private CustomerResponse mapToCustomerResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setCust_id(Math.toIntExact(customer.getCust_id()));
        response.setCust_name(customer.getCust_name());
        response.setVerification_documents(customer.isVerification_documents());
        response.setCust_mail(customer.getCust_mail());
        response.setAccountList(customer.getAccountList().stream().map(account -> {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccount_id(account.getAccount_id());
            accountResponse.setAccount_type(account.getAccount_type());
            accountResponse.setBalance(account.getBalance());
            return accountResponse;
        }).collect(Collectors.toList()));
        // Map other fields as needed
        return response;
    }
}