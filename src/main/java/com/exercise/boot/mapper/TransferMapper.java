package com.exercise.boot.mapper;

import com.exercise.boot.entity.Transfer;
import com.exercise.boot.request.TransferRequest;
import com.exercise.boot.response.TransferResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TransferMapper {
    public Transfer convertToEntity(TransferRequest transferRequest) {

        Transfer transfer = new Transfer();
        transfer.setFromAccountId(transferRequest.getFromAccountId());
        transfer.setToAccountId(transferRequest.getToAccountId());
        transfer.setAmount(transferRequest.getAmount());
        transfer.setTransferType(transferRequest.getTransferType());
        transfer.setTransferDate(LocalDate.now());
        return transfer;
    }

    public TransferResponse convertToResponse(Transfer transfer) {
        TransferResponse response = new TransferResponse();
        response.setTransferId(transfer.getTransferId());
        response.setFromAccountId(transfer.getFromAccountId());
        response.setToAccountId(transfer.getToAccountId());
        response.setAmount(transfer.getAmount());
        response.setTransferType(transfer.getTransferType());
        response.setTransferDate(transfer.getTransferDate());
        return response;
    }
}
