package com.exercise.boot.controller;

import com.exercise.boot.entity.Transfer;
import com.exercise.boot.exception.TransferNotFoundException;
import com.exercise.boot.mapper.TransferMapper;
import com.exercise.boot.request.TransferRequest;
import com.exercise.boot.response.TransferResponse;
import com.exercise.boot.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferMapper transferMapper;

    @PostMapping
    public ResponseEntity<TransferResponse> createTransfer(@RequestBody TransferRequest transferRequest) {
        logger.info("Creating a new transfer with request: {}", transferRequest);
        Transfer transfer = transferMapper.convertToEntity(transferRequest);
        Transfer savedTransfer = transferService.makeTransfer(transfer);
        TransferResponse transferResponse = transferMapper.convertToResponse(savedTransfer);
        logger.info("Transfer created successfully with response: {}", transferResponse);
        return ResponseEntity.ok(transferResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponse> getTransferById(@PathVariable int id) {
        logger.info("Fetching transfer with id: {}", id);
        Transfer transfer = transferService.getTransferById(id);
        TransferResponse transferResponse = transferMapper.convertToResponse(transfer);
        logger.info("Fetched transfer with response: {}", transferResponse);
        return ResponseEntity.ok(transferResponse);
    }

    @GetMapping
    public ResponseEntity<List<TransferResponse>> getAllTransfers() {
        logger.info("Fetching all transfers for today");
        List<TransferResponse> transfers = transferService.getTransferByDate(LocalDate.now()).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        logger.info("Fetched all transfers: {}", transfers);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/fromAccount/{fromAccountId}")
    public ResponseEntity<List<TransferResponse>> getTransfersByFromAccountId(@PathVariable int fromAccountId) {
        logger.info("Fetching transfers for fromAccountId: {}", fromAccountId);
        List<TransferResponse> transfers = transferService.getTransferByFromAccountId(fromAccountId).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        logger.info("Fetched transfers: {}", transfers);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/toAccount/{toAccountId}")
    public ResponseEntity<List<TransferResponse>> getTransfersByToAccountId(@PathVariable int toAccountId) {
        logger.info("Fetching transfers for toAccountId: {}", toAccountId);
        List<TransferResponse> transfers = transferService.getTransferByToAccountId(toAccountId).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        logger.info("Fetched transfers   : {}", transfers);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/type/{transferType}")
    public ResponseEntity<List<TransferResponse>> getTransfersByTransferType(@PathVariable String transferType) {
        logger.info("Fetching transfers for transferType: {}", transferType);
        List<TransferResponse> transfers = transferService.getTransferByTransferType(transferType).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        logger.info("Fetched transfers:: {}", transfers);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<TransferResponse>> getTransfersByDate(@PathVariable String date) {
        logger.info("Fetching transfers for date: {}", date);
        LocalDate localDate = LocalDate.parse(date);
        List<TransferResponse> transfers = transferService.getTransferByDate(localDate).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        logger.info("Fetched transfers  : {}", transfers);
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/betweenDates")
    public ResponseEntity<List<TransferResponse>> getTransfersByToDateToFromDate(
            @RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {
        logger.info("Fetching transfers between dates: {} and {}", fromDate, toDate);
        List<TransferResponse> transfers = transferService.getTransferByToDateToFromDate(fromDate, toDate).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        logger.info("Fetched transfers : {}", transfers);
        return ResponseEntity.ok(transfers);
    }

    @ExceptionHandler(TransferNotFoundException.class)
    public ResponseEntity<String> handleTransferNotFoundException(TransferNotFoundException transferNotFoundException) {
        logger.error("Transfer not found: {}", transferNotFoundException.getMessage());
        return ResponseEntity.status(404).body(transferNotFoundException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception exception) {
        logger.error("An unexpected error occurred: {}", exception.getMessage(), exception);
        return ResponseEntity.status(500).body("An unexpected error occurred: " + exception.getMessage());
    }
}
