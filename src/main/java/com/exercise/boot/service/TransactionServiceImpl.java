package com.exercise.boot.service;

import com.exercise.boot.entity.Account;
import com.exercise.boot.entity.Transaction;
import com.exercise.boot.exception.AccountNotFoundException;
import com.exercise.boot.exception.InsufficientBalanceException;
import com.exercise.boot.repository.AccountRepository;
import com.exercise.boot.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository; // Assume you have an AccountRepository

    @Override
    @Transactional
    public Transaction createTransaction(Long accountId, double amount, String transactionType) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionDate(LocalDateTime.now());

        if ("WITHDRAWAL".equalsIgnoreCase(transactionType) && account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        if ("WITHDRAWAL".equalsIgnoreCase(transactionType)) {
            account.setBalance(account.getBalance() - amount);
        } else if ("DEPOSIT".equalsIgnoreCase(transactionType)) {
            account.setBalance(account.getBalance() + amount);
        }

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findAll();
    }
}
