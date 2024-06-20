package com.exercise.boot.controller;

import com.exercise.boot.constants.BankURLConstant;
import com.exercise.boot.entity.Account;
import com.exercise.boot.entity.Customer;
import com.exercise.boot.exception.CustomerNotFoundException;
import com.exercise.boot.mapper.CustomerMapper;
import com.exercise.boot.request.CustomerListRequest;
import com.exercise.boot.request.CustomerRequest;
import com.exercise.boot.response.CustomerResponse;
import com.exercise.boot.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bank")
@ApiResponses({@ApiResponse(responseCode = "200", description = "OK"), @ApiResponse(responseCode = "400", description = "Bad Request"), @ApiResponse(responseCode = "500", description = "Internal Server Error"), @ApiResponse(responseCode = "404", description = "Not Found")})
//@EnableDiscoveryClient while registering in Eureka server we need to use @EnableDiscoveryClient
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping("/add/single-customer")
    @Operation(summary = "Create customer", description = "Create customer")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Customer created successfully"), @ApiResponse(responseCode = "400", description = "Invalid request"), @ApiResponse(responseCode = "500", description = "Internal server error"), @ApiResponse(responseCode = "404", description = "Customer not found"),

    })
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {
        if (!customerRequest.isVerification_documents()) {
            logger.error("Verification document cannot be false for customer: {}", customerRequest);
            return ResponseEntity.badRequest().body("Verification document cannot be false.");
        }

        logger.info("Converting customer request to entity");
        Customer customer = customerMapper.convertToEntity(customerRequest);
        logger.debug("Converted customer: {}", customer);

        Customer savedCustomer = customerService.createCustomerWithAccounts(customer);
        logger.info("Customer created with accounts: {}", savedCustomer);

        CustomerResponse customerResponse = customerMapper.convertToResponse(savedCustomer);

        logger.info("Returning customer response");
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping(BankURLConstant.GET_CUSTOMER_BY_ACC_ID)
    @Operation(summary = "Get customer by account id", description = "Get customer by account id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Customer found successfully"), @ApiResponse(responseCode = "400", description = "Invalid request"), @ApiResponse(responseCode = "500", description = "Internal server error"), @ApiResponse(responseCode = "404", description = "Customer not found"),})
    public ResponseEntity<?> getCustomerByAccountId(@PathVariable long accountId) {
        try {
            logger.info("Fetching customer by account ID: {}", accountId);
            CustomerResponse customerResponse = customerService.getCustomerByAccountId(accountId);
            logger.info("Customer found: {}", customerResponse);
            return ResponseEntity.ok(customerResponse);
        } catch (CustomerNotFoundException e) {
            logger.error("Customer not found for account ID: {}", accountId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while fetching customer by account ID: {}", accountId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/add/multiple-customers")
    @Operation(summary = "Create multiple customers", description = "Create multiple customers")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Customers created successfully"), @ApiResponse(responseCode = "400", description = "Invalid request"), @ApiResponse(responseCode = "500", description = "Internal server error"),})
    public ResponseEntity<?> addMultipleCustomers(@RequestBody CustomerListRequest customerListRequest) {
        List<CustomerRequest> customerRequestList = customerListRequest.getCustomers();

        logger.info("Received request to add multiple customers: {}", customerRequestList.size());

        List<CustomerRequest> validCustomerRequests = customerRequestList.stream().filter(CustomerRequest::isVerification_documents).toList();

        if (validCustomerRequests.size() != customerRequestList.size()) {
            logger.error("Some customer requests do not have verification documents");
            return ResponseEntity.badRequest().body("Some customer requests do not have verification documents.");
        }

        List<Customer> customers = validCustomerRequests.stream().map(customerMapper::convertToEntity).toList();

        List<Customer> savedCustomers = customerService.createCustomerWithAccounts(customers);
        logger.info("Created {} customers with accounts", savedCustomers.size());

        List<CustomerResponse> customerResponses = savedCustomers.stream().map(customerMapper::convertToResponse).toList();

        logger.info("Returning customer responses");
        return ResponseEntity.ok(customerResponses);
    }

    @GetMapping("/customer/{customer_id}")
    @Operation(summary = "Get customer by customer id", description = "Get customer by customer id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Customer found successfully"), @ApiResponse(responseCode = "400", description = "Invalid request"), @ApiResponse(responseCode = "500", description = "Internal server error"), @ApiResponse(responseCode = "404", description = "Customer not found"),})
    public ResponseEntity<CustomerResponse> getCustomerByCustomerId(@PathVariable long customer_id) {
        logger.info("Fetching customer by customer  ID:   {}", customer_id);
        CustomerResponse response = customerService.getCustomerByCustomerId(customer_id);
        logger.info("Customer found with the customer ID: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/customer/{customer_id}")
    @Operation(summary = "Update customer by customer id", description = "Update customer by customer id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Customer updated successfully"), @ApiResponse(responseCode = "400", description = "Invalid request"), @ApiResponse(responseCode = "500", description = "Internal server error"), @ApiResponse(responseCode = "404", description = "Customer not found"),})
    public ResponseEntity<?> updateCustomer(@PathVariable("customer_id") long customerId, @RequestBody CustomerRequest updatedCustomerRequest) {
        try {
            logger.info("Fetching customer by customer ID:   {}", customerId);
            CustomerResponse customerResponse = customerService.getCustomerByCustomerId(customerId);
            logger.info("Customer  found:  {}", customerResponse);

            // Convert updatedCustomerRequest to Customer entity
            Customer updatedCustomer = customerMapper.convertToEntity(updatedCustomerRequest);

            updatedCustomer.setCust_name(customerResponse.getCust_name());
            updatedCustomer.setCust_mail(customerResponse.getCust_mail());
            updatedCustomer.setVerification_documents(customerResponse.isVerification_documents());
            updatedCustomer.setAccountList(customerResponse.getAccountList().stream().map(accountResponse -> {
                Account account = new Account();
                account.setAccount_id(accountResponse.getAccount_id());
                account.setAccount_type(accountResponse.getAccount_type());
                account.setBalance(accountResponse.getBalance());
                return account;
            }).toList()); // Requires Java 16 or later, use .collect(Collectors.toList()) for earlier versions

            // Update the customer using the service
            Customer savedCustomer = customerService.updateCustomer(updatedCustomer);
            logger.info("Customer updated: {}", savedCustomer);

            // Convert updated Customer to CustomerResponse
            CustomerResponse updatedCustomerResponse = customerMapper.convertToResponse(savedCustomer);

            return ResponseEntity.ok(updatedCustomerResponse);
        } catch (CustomerNotFoundException e) {
            logger.error("Customer not found for customer ID: {}", customerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while updating customer for customer ID: {}", customerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/customer/{customer_id}")
    @Operation(summary = "Delete customer by customer id", description = "Delete customer by customer id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Customer deleted successfully"), @ApiResponse(responseCode = "400", description = "Invalid request"), @ApiResponse(responseCode = "500", description = "Internal server error"), @ApiResponse(responseCode = "404", description = "Customer not found"),})
    public ResponseEntity<?> deleteCustomer(@PathVariable("customer_id") long customerId) {
        try {
            logger.info("Deleting customer with ID: {}", customerId);
            customerService.deleteCustomer(customerId);
            logger.info("Customer deleted successfully");

            return ResponseEntity.ok("Customer with ID " + customerId + " deleted successfully");
        } catch (CustomerNotFoundException e) {
            logger.error("Customer not found for ID: {}", customerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while deleting customer with ID: {}", customerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/customers/starts-with/{letter}")
    @Operation(summary = "Get customers by first letter of name", description = "Get customers by first letter of name")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Customers found successfully"), @ApiResponse(responseCode = "400", description = "Invalid request"), @ApiResponse(responseCode = "500", description = "Internal server error"),})
    public ResponseEntity<List<CustomerResponse>> getCustomersByFirstLetterOfName(@PathVariable("letter") char letter) {
        try {
            logger.info("Fetching customers whose name starts with: {}", letter);
            List<CustomerResponse> customers = customerService.getCustomersByFirstLetterOfName(letter);
            logger.info("Found {} customers", customers.size());

            return ResponseEntity.ok(customers);
        } catch (CustomerNotFoundException e) {
            logger.error("No customers found whose name starts with: {}", letter);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        } catch (Exception e) {
            logger.error("An error occurred while fetching customers by first letter of name: {}", letter, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }

    @GetMapping("/customers")
    @Operation(summary = "Get all customers", description = "Get all customers")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Customers found successfully"), @ApiResponse(responseCode = "400", description = "Invalid request"), @ApiResponse(responseCode = "500", description = "Internal server error"),})
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            logger.error("An error occurred while fetching all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }


}





