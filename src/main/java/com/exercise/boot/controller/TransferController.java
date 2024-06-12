package com.exercise.boot.controller;

import com.exercise.boot.entity.Transfer;
import com.exercise.boot.exception.TransferNotFoundException;
import com.exercise.boot.mapper.TransferMapper;
import com.exercise.boot.request.TransferRequest;
import com.exercise.boot.response.TransferResponse;
import com.exercise.boot.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;
    @Autowired
    private TransferMapper transferMapper;


    @PostMapping
    public ResponseEntity<TransferResponse> createTransfer(@RequestBody TransferRequest transferRequest) {
        Transfer transfer = transferMapper.convertToEntity(transferRequest);
        Transfer savedTransfer = transferService.makeTransfer(transfer);
        TransferResponse transferResponse = transferMapper.convertToResponse(savedTransfer);
        return ResponseEntity.ok(transferResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferResponse> getTransferById(@PathVariable int id) {
        Transfer transfer = transferService.getTransferById(id);
        TransferResponse transferResponse = transferMapper.convertToResponse(transfer);
        return ResponseEntity.ok(transferResponse);
    }

    @GetMapping
    public ResponseEntity<List<TransferResponse>> getAllTransfers() {
        List<TransferResponse> transfers = transferService.getTransferByDate(LocalDate.now()).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/fromAccount/{fromAccountId}")
    public ResponseEntity<List<TransferResponse>> getTransfersByFromAccountId(@PathVariable int fromAccountId) {
        List<TransferResponse> transfers = transferService.getTransferByFromAccountId(fromAccountId).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/toAccount/{toAccountId}")
    public ResponseEntity<List<TransferResponse>> getTransfersByToAccountId(@PathVariable int toAccountId) {
        List<TransferResponse> transfers = transferService.getTransferByToAccountId(toAccountId).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/type/{transferType}")
    public ResponseEntity<List<TransferResponse>> getTransfersByTransferType(@PathVariable String transferType) {
        List<TransferResponse> transfers = transferService.getTransferByTransferType(transferType).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<TransferResponse>> getTransfersByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<TransferResponse> transfers = transferService.getTransferByDate(localDate).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transfers);
    }

    @GetMapping("/betweenDates")
    public ResponseEntity<List<TransferResponse>> getTransfersByToDateToFromDate(
            @RequestParam LocalDate fromDate, @RequestParam LocalDate toDate) {

        List<TransferResponse> transfers = transferService.getTransferByToDateToFromDate(fromDate, toDate).stream()
                .map(transferMapper::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transfers);
    }

    @ExceptionHandler(TransferNotFoundException.class)
    public ResponseEntity<String> handleTransferNotFoundException(TransferNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(500).body("An unexpected error occurred: " + ex.getMessage());
    }
}
