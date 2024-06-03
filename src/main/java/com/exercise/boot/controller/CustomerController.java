package com.exercise.boot.controller;

import com.exercise.boot.constants.BankURLConstant;
import com.exercise.boot.entity.Customer;
import com.exercise.boot.exception.CustomerNotFoundException;
import com.exercise.boot.mapper.CustomerMapper;
import com.exercise.boot.request.CustomerListRequest;
import com.exercise.boot.request.CustomerRequest;
import com.exercise.boot.response.CustomerResponse;
import com.exercise.boot.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping("/add/single-customer")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {
        if (!customerRequest.isVerification_documents()) {
            return ResponseEntity.badRequest().body("Verification document cannot be false.");
        }
        Customer customer = customerMapper.convertToEntity(customerRequest);
        Customer savedCustomer = customerService.createCustomerWithAccounts(customer);
        CustomerResponse customerResponse = customerMapper.convertToResponse(savedCustomer);

        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping(BankURLConstant.GET_CUSTOMER_BY_ACC_ID)
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

    @PostMapping("/add/multiple-customers")
    public ResponseEntity<?> addMultipleCustomers(@RequestBody CustomerListRequest customerListRequest) {
        List<CustomerRequest> customerRequestList = customerListRequest.getCustomers();

        List<CustomerRequest> validCustomerRequests = customerRequestList.stream().filter(CustomerRequest::isVerification_documents).toList();

        if (validCustomerRequests.size() != customerRequestList.size()) {
            return ResponseEntity.badRequest().body("Some customer requests do not have verification documents.");
        }

        List<Customer> customers = validCustomerRequests.stream().map(customerMapper::convertToEntity).toList();

        List<Customer> savedCustomers = customerService.createCustomerWithAccounts(customers);
        List<CustomerResponse> customerResponses = savedCustomers.stream().map(customerMapper::convertToResponse).toList();

        return ResponseEntity.ok(customerResponses);
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<CustomerResponse> getCustomerByCustomerId(@PathVariable long customer_id) {
        CustomerResponse response = customerService.getCustomerByCustomerId(customer_id);
        return ResponseEntity.ok(response);

    }
}





