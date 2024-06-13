package com.exercise.boot;

import com.exercise.boot.controller.TransferController;
import com.exercise.boot.entity.Transfer;
import com.exercise.boot.mapper.TransferMapper;
import com.exercise.boot.request.TransferRequest;
import com.exercise.boot.response.TransferResponse;
import com.exercise.boot.service.TransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Add this annotation to enable Mockito
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

    @Mock
    private Logger mockLogger;
    @Mock
    private TransferService mockTransferService;

    @Mock
    private TransferMapper mockTransferMapper;

    @InjectMocks
    private TransferController mockTransferController;

    // Unit test for createTransfer
    @Test
    public void testCreateTransfer() {
        TransferRequest transferRequest = new TransferRequest();
        Transfer transfer = new Transfer();
        Transfer savedTransfer = new Transfer();
        TransferResponse transferResponse = new TransferResponse();

        when(mockTransferMapper.convertToEntity(transferRequest)).thenReturn(transfer);
        when(mockTransferService.makeTransfer(transfer)).thenReturn(savedTransfer);
        when(mockTransferMapper.convertToResponse(savedTransfer)).thenReturn(transferResponse);

        ResponseEntity<TransferResponse> response = mockTransferController.createTransfer(transferRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponse, response.getBody());
    }

    // Unit test for getTransferById
    @Test
    public void testGetTransferById() {
        int id = 1;
        Transfer transfer = new Transfer();
        TransferResponse transferResponse = new TransferResponse();

        when(mockTransferService.getTransferById(id)).thenReturn(transfer);
        when(mockTransferMapper.convertToResponse(transfer)).thenReturn(transferResponse);

        ResponseEntity<TransferResponse> response = mockTransferController.getTransferById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponse, response.getBody());
    }

    // Unit test for getAllTransfers
    @Test
    public void testGetAllTransfers() {
        LocalDate currentDate = LocalDate.now();
        List<Transfer> transferList = List.of(new Transfer());
        List<TransferResponse> transferResponseList = List.of(new TransferResponse());

        when(mockTransferService.getTransferByDate(currentDate)).thenReturn(transferList);
        when(mockTransferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = mockTransferController.getAllTransfers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByFromAccountId
    @Test
    public void testGetTransfersByFromAccountId() {
        int fromAccountId = 1;
        List<Transfer> transferList = List.of(new Transfer());
        List<TransferResponse> transferResponseList = List.of(new TransferResponse());

        when(mockTransferService.getTransferByFromAccountId(fromAccountId)).thenReturn(transferList);
        when(mockTransferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = mockTransferController.getTransfersByFromAccountId(fromAccountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByToAccountId
    @Test
    public void testGetTransfersByToAccountId() {
        int toAccountId = 1;
        List<Transfer> transferList = List.of(new Transfer());
        List<TransferResponse> transferResponseList = List.of(new TransferResponse());

        when(mockTransferService.getTransferByToAccountId(toAccountId)).thenReturn(transferList);
        when(mockTransferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = mockTransferController.getTransfersByToAccountId(toAccountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByTransferType
    @Test
    public void testGetTransfersByTransferType() {
        String transferType = "type";
        List<Transfer> transferList = List.of(new Transfer());
        List<TransferResponse> transferResponseList = List.of(new TransferResponse());

        when(mockTransferService.getTransferByTransferType(transferType)).thenReturn(transferList);
        when(mockTransferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = mockTransferController.getTransfersByTransferType(transferType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByDate
    @Test
    public void testGetTransfersByDate() {
        LocalDate date = LocalDate.now();
        List<Transfer> transferList = List.of(new Transfer());
        List<TransferResponse> transferResponseList = List.of(new TransferResponse());

        when(mockTransferService.getTransferByDate(date)).thenReturn(transferList);
        when(mockTransferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = mockTransferController.getTransfersByDate(String.valueOf(date));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByToDateToFromDate
    @Test
    public void testGetTransfersByToDateToFromDate() {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        List<Transfer> transferList = List.of(new Transfer());
        List<TransferResponse> transferResponseList = List.of(new TransferResponse());

        when(mockTransferService.getTransferByToDateToFromDate(fromDate, toDate)).thenReturn(transferList);
        when(mockTransferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = mockTransferController.getTransfersByToDateToFromDate(fromDate, toDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }
}