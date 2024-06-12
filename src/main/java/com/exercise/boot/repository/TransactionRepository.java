package com.exercise.boot.repository;

import com.exercise.boot.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByTransactionDate(LocalDate date);

    List<Transaction> findByTransactionType(String transactionType);

    @Query(nativeQuery = true, value = "SELECT * FROM transaction WHERE account_id = ?1 AND transaction_type = ?2")
    List<Transaction> findAllByAccount_AccountIdAndTransactionType(Long accountId, String transactionType);
}

