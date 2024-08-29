package com.exercise.boot.test;

import com.exercise.boot.controller.CustomerController;
import com.exercise.boot.entity.Account;
import com.exercise.boot.entity.Customer;
import com.exercise.boot.exception.CustomerNotFoundException;
import com.exercise.boot.mapper.CustomerMapper;
import com.exercise.boot.request.CustomerListRequest;
import com.exercise.boot.request.CustomerRequest;
import com.exercise.boot.response.CustomerResponse;
import com.exercise.boot.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper customerMapper;

    private CustomerRequest customerRequest;
    private Customer customer;
    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        customerRequest = new CustomerRequest();
        customerRequest.setVerification_documents(true);

        customer = new Customer();
        customer.setCust_id(1L);

        customerResponse = new CustomerResponse();
        customerResponse.setCust_id(1);
    }

    @Test
    public void testCreateCustomer_Success() {
        when(customerMapper.convertToEntity(customerRequest)).thenReturn(customer);
        when(customerService.createCustomerWithAccounts(customer)).thenReturn(customer);
        when(customerMapper.convertToResponse(customer)).thenReturn(customerResponse);

        ResponseEntity<?> response = customerController.createCustomer(customerRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    public void testCreateCustomer_InvalidRequest() {
        customerRequest.setVerification_documents(false);
        ResponseEntity<?> response = customerController.createCustomer(customerRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Verification document cannot be false.", response.getBody());
    }

    @Test
    public void testGetCustomerByAccountId_Success() throws CustomerNotFoundException {
        when(customerService.getCustomerByAccountId(1L)).thenReturn(customerResponse);

        ResponseEntity<?> response = customerController.getCustomerByAccountId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    public void testGetCustomerByAccountId_NotFound() throws CustomerNotFoundException {
        when(customerService.getCustomerByAccountId(1L)).thenThrow(new CustomerNotFoundException("Customer not found"));

        ResponseEntity<?> response = customerController.getCustomerByAccountId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }

    @Test
    public void testAddMultipleCustomers_Success() {
        List<CustomerRequest> customerRequestList = new ArrayList<>();
        customerRequestList.add(customerRequest);
        CustomerListRequest customerListRequest = new CustomerListRequest();
        customerListRequest.setCustomers(customerRequestList);

        List<Customer> customers = List.of(customer);
        List<CustomerResponse> customerResponses = List.of(customerResponse);

        when(customerMapper.convertToEntity(customerRequest)).thenReturn(customer);
        when(customerService.createCustomerWithAccounts(customers)).thenReturn(customers);
        when(customerMapper.convertToResponse(any(Customer.class))).thenReturn(customerResponse);

        ResponseEntity<?> response = customerController.addMultipleCustomers(customerListRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponses, response.getBody());
    }

    @Test
    public void testDeleteCustomer_Success() {
        doNothing().when(customerService).deleteCustomer(1L);

        ResponseEntity<?> response = customerController.deleteCustomer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer with ID 1 deleted successfully", response.getBody());
    }

    @Test
    public void testDeleteCustomer_NotFound() throws CustomerNotFoundException {
        doThrow(new CustomerNotFoundException("Customer not found")).when(customerService).deleteCustomer(1L);

        ResponseEntity<?> response = customerController.deleteCustomer(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }

    @Test
    public void testUpdateCustomer_Success() throws CustomerNotFoundException {
        when(customerService.getCustomerByCustomerId(1L)).thenReturn(customerResponse);
        when(customerMapper.convertToEntity(any(CustomerRequest.class))).thenReturn(customer);
        when(customerService.updateCustomer(any(Customer.class))).thenReturn(customer);
        when(customerMapper.convertToResponse(any(Customer.class))).thenReturn(customerResponse);

        ResponseEntity<?> response = customerController.updateCustomer(1L, customerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    public void testUpdateCustomer_NotFound() throws CustomerNotFoundException {
        when(customerService.getCustomerByCustomerId(1L)).thenThrow(new CustomerNotFoundException("Customer not found"));

        ResponseEntity<?> response = customerController.updateCustomer(1L, customerRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }

    @Test
    public void testUpdateCustomerOnlyNameById_Success() throws CustomerNotFoundException {
        doNothing().when(customerService).updateCustomerOnlyNameById(1L, "John");
        when(customerService.getCustomerByCustomerId(1L)).thenReturn(customerResponse);

        ResponseEntity<?> response = customerController.updateCustomerOnlyNameById(1L, "John");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    public void testUpdateCustomerOnlyNameById_NotFound() throws CustomerNotFoundException {
        doThrow(new CustomerNotFoundException("Customer not found")).when(customerService).updateCustomerOnlyNameById(1L, "John");

        ResponseEntity<?> response = customerController.updateCustomerOnlyNameById(1L, "John");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
