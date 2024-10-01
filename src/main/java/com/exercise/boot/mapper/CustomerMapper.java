package com.exercise.boot.mapper;

import com.exercise.boot.entity.Account;
import com.exercise.boot.entity.Customer;
import com.exercise.boot.request.AccountRequest;
import com.exercise.boot.request.CustomerRequest;
import com.exercise.boot.response.AccountResponse;
import com.exercise.boot.response.CustomerResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {
    public Customer convertToEntity(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setCust_name(customerRequest.getCust_name());
        customer.setVerification_documents(customerRequest.getVerification_documents());
        customer.setCust_mail(customerRequest.getCust_mail());

        List<Account> accounts = new ArrayList<>();
        for (AccountRequest accountRequest : customerRequest.getAccountList()) {
            Account account = new Account();
            account.setAccount_type(accountRequest.getAccount_type());
            account.setBalance(accountRequest.getBalance());
            accounts.add(account);
        }
        customer.setAccountList(accounts);
        return customer;
    }

    public CustomerResponse convertToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setCust_id(Math.toIntExact(customer.getCust_id()));
        response.setCust_name(customer.getCust_name());
        response.setVerification_documents(customer.getVerification_documents());
        response.setCust_mail(customer.getCust_mail());

        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : customer.getAccountList()) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setAccount_id(account.getAccount_id());
            accountResponse.setAccount_type(account.getAccount_type());
            accountResponse.setBalance(account.getBalance());
            accountResponses.add(accountResponse);
        }
        response.setAccountList(accountResponses);
        return response;
    }
}
