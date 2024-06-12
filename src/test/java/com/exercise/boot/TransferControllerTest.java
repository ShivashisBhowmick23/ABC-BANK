package com.exercise.boot;

// Import statements for unit test

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

// Add this annotation to enable Mockito
@ExtendWith(MockitoExtension.class)
public class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @Mock
    private TransferMapper transferMapper;

    @InjectMocks
    private TransferController transferController;

    // Unit test for createTransfer
    @Test
    public void testCreateTransfer() {
        TransferRequest transferRequest = new TransferRequest();
        Transfer transfer = new Transfer();
        Transfer savedTransfer = new Transfer();
        TransferResponse transferResponse = new TransferResponse();

        when(transferMapper.convertToEntity(transferRequest)).thenReturn(transfer);
        when(transferService.makeTransfer(transfer)).thenReturn(savedTransfer);
        when(transferMapper.convertToResponse(savedTransfer)).thenReturn(transferResponse);

        ResponseEntity<TransferResponse> response = transferController.createTransfer(transferRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponse, response.getBody());
    }

    // Unit test for getTransferById
    @Test
    public void testGetTransferById() {
        int id = 1;
        Transfer transfer = new Transfer();
        TransferResponse transferResponse = new TransferResponse();

        when(transferService.getTransferById(id)).thenReturn(transfer);
        when(transferMapper.convertToResponse(transfer)).thenReturn(transferResponse);

        ResponseEntity<TransferResponse> response = transferController.getTransferById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponse, response.getBody());
    }

    // Unit test for getAllTransfers
    @Test
    public void testGetAllTransfers() {
        LocalDate currentDate = LocalDate.now();
        List<Transfer> transferList = Arrays.asList(new Transfer());
        List<TransferResponse> transferResponseList = Arrays.asList(new TransferResponse());

        when(transferService.getTransferByDate(currentDate)).thenReturn(transferList);
        when(transferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = transferController.getAllTransfers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByFromAccountId
    @Test
    public void testGetTransfersByFromAccountId() {
        int fromAccountId = 1;
        List<Transfer> transferList = Arrays.asList(new Transfer());
        List<TransferResponse> transferResponseList = Arrays.asList(new TransferResponse());

        when(transferService.getTransferByFromAccountId(fromAccountId)).thenReturn(transferList);
        when(transferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = transferController.getTransfersByFromAccountId(fromAccountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByToAccountId
    @Test
    public void testGetTransfersByToAccountId() {
        int toAccountId = 1;
        List<Transfer> transferList = Arrays.asList(new Transfer());
        List<TransferResponse> transferResponseList = Arrays.asList(new TransferResponse());

        when(transferService.getTransferByToAccountId(toAccountId)).thenReturn(transferList);
        when(transferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = transferController.getTransfersByToAccountId(toAccountId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByTransferType
    @Test
    public void testGetTransfersByTransferType() {
        String transferType = "type";
        List<Transfer> transferList = Arrays.asList(new Transfer());
        List<TransferResponse> transferResponseList = Arrays.asList(new TransferResponse());

        when(transferService.getTransferByTransferType(transferType)).thenReturn(transferList);
        when(transferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = transferController.getTransfersByTransferType(transferType);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByDate
    @Test
    public void testGetTransfersByDate() {
        LocalDate date = LocalDate.now();
        List<Transfer> transferList = Arrays.asList(new Transfer());
        List<TransferResponse> transferResponseList = Arrays.asList(new TransferResponse());

        when(transferService.getTransferByDate(date)).thenReturn(transferList);
        when(transferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = transferController.getTransfersByDate(String.valueOf(date));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

    // Unit test for getTransfersByToDateToFromDate
    @Test
    public void testGetTransfersByToDateToFromDate() {
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = LocalDate.now();
        List<Transfer> transferList = Arrays.asList(new Transfer());
        List<TransferResponse> transferResponseList = Arrays.asList(new TransferResponse());

        when(transferService.getTransferByToDateToFromDate(fromDate, toDate)).thenReturn(transferList);
        when(transferMapper.convertToResponse(any())).thenReturn(transferResponseList.get(0));

        ResponseEntity<List<TransferResponse>> response = transferController.getTransfersByToDateToFromDate(fromDate, toDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferResponseList, response.getBody());
    }

}
