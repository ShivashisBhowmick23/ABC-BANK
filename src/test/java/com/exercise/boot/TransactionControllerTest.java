package com.exercise.boot;

import com.exercise.boot.controller.TransactionController;
import com.exercise.boot.entity.Account;
import com.exercise.boot.entity.Transaction;
import com.exercise.boot.exception.AccountNotFoundException;
import com.exercise.boot.exception.InsufficientBalanceException;
import com.exercise.boot.request.TransactionRequest;
import com.exercise.boot.response.TransactionResponse;
import com.exercise.boot.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private TransactionRequest transactionRequest;
    private Transaction transaction;
    private List<Transaction> transactionList;

    @BeforeEach
    void setUp() {
        // Initialize the common test data
        transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(1L);
        transactionRequest.setAmount(100.0);
        transactionRequest.setTransactionType("DEPOSIT");

        Account account = new Account();
        account.setAccount_id(1L);
        account.setBalance(1000.0);

        transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setAmount(100.0);
        transaction.setTransactionType("DEPOSIT");
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAccount(account);

        transactionList = Collections.singletonList(transaction);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionId(1L);
        transactionResponse.setAccountId(1L);
        transactionResponse.setAmount(100.0);
        transactionResponse.setTransactionType("DEPOSIT");
        transactionResponse.setTransactionDate(LocalDate.now());

        List<TransactionResponse> transactionResponseList = Collections.singletonList(transactionResponse);
    }

    @Test
    public void testCreateTransaction() {
        when(transactionService.createTransaction(anyLong(), anyDouble(), anyString())).thenReturn(transaction);

        ResponseEntity<Transaction> response = transactionController.createTransaction(transactionRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getTransactionId());
    }

    @Test
    public void testCreateTransaction_InsufficientBalance() {
        transactionRequest.setAmount(1000.0);
        transactionRequest.setTransactionType("WITHDRAWAL");

        doThrow(new InsufficientBalanceException("Insufficient balance")).when(transactionService).createTransaction(anyLong(), anyDouble(), anyString());

        Exception exception = assertThrows(InsufficientBalanceException.class, () -> {
            transactionController.createTransaction(transactionRequest);
        });

        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    public void testCreateTransaction_AccountNotFound() {
        doThrow(new AccountNotFoundException("Account not found")).when(transactionService).createTransaction(anyLong(), anyDouble(), anyString());

        Exception exception = assertThrows(AccountNotFoundException.class, () -> {
            transactionController.createTransaction(transactionRequest);
        });

        assertEquals("Account not found", exception.getMessage());
    }

    @Test
    public void testGetTransactionsByAccountId() {
        Long accountId = 1L;
        when(transactionService.getTransactionsByAccountId(accountId)).thenReturn(transactionList);

        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByAccountId(accountId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getTransactionId());
    }

    @Test
    public void testGetTransactionsByAccountId_EmptyList() {
        Long accountId = 1L;
        when(transactionService.getTransactionsByAccountId(accountId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Transaction>> response = transactionController.getTransactionsByAccountId(accountId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testGetTransactionsByDate() {
        String date = "2023-01-01";
        LocalDate transactionDate = LocalDate.parse(date);
        when(transactionService.getTransactionsByDate(transactionDate)).thenReturn(transactionList);

        ResponseEntity<?> response = transactionController.getTransactionsByDate(date);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, ((List<Transaction>) response.getBody()).size());
        assertEquals(1L, ((List<Transaction>) response.getBody()).get(0).getTransactionId());
    }

    @Test
    public void testGetTransactionsByDate_InvalidDate() {
        String invalidDate = "invalid-date";

        ResponseEntity<?> response = transactionController.getTransactionsByDate(invalidDate);

        assertNotNull(response);
        assertEquals(400, response.getStatusCode().value());
        assertEquals("Invalid date format. Please use YYYY-MM-DD.", response.getBody());
    }

    @Test
    public void testGetTransactionsByTransactionType() {
        String transactionType = "DEPOSIT";
        when(transactionService.getTransactionsByTransactionType(transactionType)).thenReturn(transactionList);

        ResponseEntity<List<TransactionResponse>> response = transactionController.getTransactionsByTransactionType(transactionType);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getTransactionId());
    }

    @Test
    public void testGetTransactionsByAccountIdAndTransactionType() {
        Long accountId = 1L;
        String transactionType = "DEPOSIT";
        when(transactionService.getTransactionsByAccountIdAndTransactionType(accountId, transactionType)).thenReturn(transactionList);

        ResponseEntity<List<TransactionResponse>> response = transactionController.getTransactionsByAccountIdAndTransactionType(accountId, transactionType);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getTransactionId());
    }

    @Test
    public void testGetTransactionsByAccountIdAndTransactionType_Error() {
        Long accountId = 1L;
        String transactionType = "DEPOSIT";
        doThrow(new RuntimeException("Test exception")).when(transactionService).getTransactionsByAccountIdAndTransactionType(anyLong(), anyString());

        ResponseEntity<List<TransactionResponse>> response = transactionController.getTransactionsByAccountIdAndTransactionType(accountId, transactionType);

        assertNotNull(response);
        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(0L, response.getBody().get(0).getTransactionId());
        assertEquals(0.0, response.getBody().get(0).getAmount());
        assertEquals(transactionType, response.getBody().get(0).getTransactionType());
    }
}
