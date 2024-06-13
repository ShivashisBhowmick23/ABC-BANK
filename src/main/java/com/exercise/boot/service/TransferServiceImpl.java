package com.exercise.boot.service;

import com.exercise.boot.entity.Transfer;
import com.exercise.boot.exception.TransferNotFoundException;
import com.exercise.boot.repository.TransferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    private final TransferRepository transferRepository;

    @Autowired
    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public Transfer makeTransfer(Transfer transfer) {
        logger.info("Making a transfer: {}", transfer);
        Transfer savedTransfer = transferRepository.save(transfer);
        logger.info("Transfer made successfully: {}", savedTransfer);
        return savedTransfer;
    }

    @Override
    public Transfer getTransferById(int transferId) throws TransferNotFoundException {
        logger.info("Fetching transfer with ID: {}", transferId);
        Transfer transfer = transferRepository.findById(transferId).orElseThrow(() ->
                new TransferNotFoundException("Transfer with ID " + transferId + " not found"));
        logger.info("Fetched transfer: {}", transfer);
        return transfer;
    }

    @Override
    public List<Transfer> getTransferByFromAccountId(int fromAccountId) {
        logger.info("Fetching transfers with fromAccountId: {}", fromAccountId);
        List<Transfer> transfers = transferRepository.findAll().stream()
                .filter(transfer -> transfer.getFromAccountId() == fromAccountId)
                .toList();
        logger.info("Fetched transfers by fromAccount id: {}", transfers);
        return transfers;
    }

    @Override
    public List<Transfer> getTransferByTransferType(String transferType) {
        logger.info("Fetching transfers with transferType: {}", transferType);
        List<Transfer> transfers = transferRepository.findAll().stream()
                .filter(transfer -> transfer.getTransferType().equalsIgnoreCase(transferType))
                .toList();
        logger.info("Fetched transfers by TransferType : {}", transfers);
        return transfers;
    }

    @Override
    public List<Transfer> getTransferByDate(LocalDate date) {
        logger.info("Fetching transfers with date: {}", date);
        List<Transfer> transfers = transferRepository.findAll().stream()
                .filter(transfer -> transfer.getTransferDate().isEqual(date))
                .toList();
        logger.info("Fetched transfers by Date : {}", transfers);
        return transfers;
    }

    @Override
    public List<Transfer> getTransferByToAccountId(int toAccountId) {
        logger.info("Fetching transfers with toAccountId: {}", toAccountId);
        List<Transfer> transfers = transferRepository.findAll().stream()
                .filter(transfer -> transfer.getToAccountId() == toAccountId)
                .toList();
        logger.info("Fetched transfers toAccountId: {}", transfers);
        return transfers;
    }

    @Override
    public List<Transfer> getTransferByToDateToFromDate(LocalDate fromDate, LocalDate toDate) {
        logger.info("Fetching transfers between dates: {} and {}", fromDate, toDate);
        List<Transfer> transfers = transferRepository.findAll().stream()
                .filter(transfer -> !transfer.getTransferDate().isBefore(fromDate) && !transfer.getTransferDate().isAfter(toDate))
                .toList();
        logger.info("Fetched transfers by to date and from date: {}", transfers);
        return transfers;
    }
}
