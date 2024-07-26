package com.exercise.boot.service;

import com.exercise.boot.entity.Account;
import com.exercise.boot.entity.Transaction;
import com.exercise.boot.exception.AccountNotFoundException;
import com.exercise.boot.exception.InsufficientBalanceException;
import com.exercise.boot.repository.AccountRepository;
import com.exercise.boot.repository.TransactionRepository;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Transaction createTransaction(Long accountId, double amount, String transactionType) {
        logger.info("Creating transaction for accountId: {}, amount: {}, transactionType: {}", accountId, amount, transactionType);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        logger.info("Account found: {}", account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionDate(LocalDate.now());

        if ("WITHDRAWAL".equalsIgnoreCase(transactionType) && account.getBalance() < amount) {
            logger.error("Insufficient balance for accountId: {}", accountId);
            throw new InsufficientBalanceException("Insufficient balance");
        }

        if ("WITHDRAWAL".equalsIgnoreCase(transactionType)) {
            account.setBalance(account.getBalance() - amount);
            logger.info("Withdrawn amount: {}. New balance: {}", amount, account.getBalance());
        } else if ("DEPOSIT".equalsIgnoreCase(transactionType)) {
            account.setBalance(account.getBalance() + amount);
            logger.info("Deposited amount: {}. New balance: {}", amount, account.getBalance());
        }

        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        logger.info("Transaction created successfully: {}", savedTransaction);

        return savedTransaction;
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        logger.info("Fetching transactions for accountId: {}", accountId);
        List<Transaction> transactions = transactionRepository.findAll().stream().filter(transaction -> transaction.getAccount().getAccount_id().equals(accountId)).collect(Collectors.toList());
        logger.info("Fetched transactions  : {}", transactions);
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByDate(LocalDate date) {
        logger.info("Fetching transactions for date: {}", date);
        List<Transaction> transactions = transactionRepository.findAllByTransactionDate(date);
        logger.info("Fetched transactions : {}", transactions);
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByTransactionType(String transactionType) {
        logger.info("Fetching transactions for transactionType: {}", transactionType);
        List<Transaction> transactions = transactionRepository.findByTransactionType(transactionType);
        logger.info("Fetched transactions: {}", transactions);
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByAccountIdAndTransactionType(Long accountId, String transactionType) {
        logger.info("Fetching transactions for accountId: {} and transactionType: {}", accountId, transactionType);
        List<Transaction> transactions = transactionRepository.findAllByAccount_AccountIdAndTransactionType(accountId, transactionType);
        logger.info("Fetched transactions   : {}", transactions);
        return transactions;
    }
}
