package com.exercise.boot.controller;

import com.exercise.boot.entity.Customer;
import com.exercise.boot.exception.CustomerNotFoundException;
import com.exercise.boot.mapper.CustomerMapper;
import com.exercise.boot.request.CustomerRequest;
import com.exercise.boot.response.CustomerResponse;
import com.exercise.boot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {
        if (!customerRequest.isVerification_documents()) {
            return ResponseEntity.badRequest().body("Verification document cannot be false.");
        }
        Customer customer = customerMapper.convertToEntity(customerRequest);
        Customer savedCustomer = customerService.createCustomerWithAccounts(customer);
        CustomerResponse customerResponse = customerMapper.convertToResponse(savedCustomer);

        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/byAccount/{accountId}")
    public ResponseEntity<?> getCustomerByAccountId(@PathVariable long accountId) {
        try {
            CustomerResponse customerResponse = customerService.getCustomerByAccountId(accountId);
            return ResponseEntity.ok(customerResponse);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}

