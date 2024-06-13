package com.exercise.boot.service;

import com.exercise.boot.entity.Transfer;
import com.exercise.boot.exception.TransferNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface TransferService {
    Transfer makeTransfer(Transfer transfer);

    Transfer getTransferById(int transferId) throws TransferNotFoundException;

    List<Transfer> getTransferByFromAccountId(int fromAccountId);

    List<Transfer> getTransferByTransferType(String transferType);

    List<Transfer> getTransferByDate(LocalDate date);

    List<Transfer> getTransferByToAccountId(int toAccountId);

    List<Transfer> getTransferByToDateToFromDate(LocalDate fromDate, LocalDate toDate);

}
