package com.exercise.boot.service;

import com.exercise.boot.entity.Transfer;
import com.exercise.boot.exception.TransferNotFoundException;
import com.exercise.boot.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    @Autowired
    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public Transfer makeTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public Transfer getTransferById(int transferId) throws TransferNotFoundException {
        return transferRepository.findById(transferId).orElseThrow(() ->
                new TransferNotFoundException("Transfer with ID " + transferId + " not found"));
    }

    @Override
    public List<Transfer> getTransferByFromAccountId(int fromAccountId) {
        return transferRepository.findAll().stream()
                .filter(transfer -> transfer.getFromAccountId() == fromAccountId)
                .toList();
    }

    @Override
    public List<Transfer> getTransferByTransferType(String transferType) {
        return transferRepository.findAll().stream()
                .filter(transfer -> transfer.getTransferType().equalsIgnoreCase(transferType))
                .toList();
    }

    @Override
    public List<Transfer> getTransferByDate(LocalDate date) {
        return transferRepository.findAll().stream()
                .filter(transfer -> transfer.getTransferDate().isEqual(date))
                .toList();
    }

    @Override
    public List<Transfer> getTransferByToAccountId(int toAccountId) {
        return transferRepository.findAll().stream()
                .filter(transfer -> transfer.getToAccountId() == toAccountId)
                .toList();
    }

    @Override
    public List<Transfer> getTransferByToDateToFromDate(LocalDate fromDate, LocalDate toDate) {
        return transferRepository.findAll().stream()
                .filter(transfer -> !transfer.getTransferDate().isBefore(fromDate) && !transfer.getTransferDate().isAfter(toDate))
                .toList();
    }
}
