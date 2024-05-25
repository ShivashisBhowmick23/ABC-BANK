package com.exercise.boot.repository;

import com.exercise.boot.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Define custom query methods if needed
}

