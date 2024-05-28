package com.exercise.boot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    private double amount;
    private String transactionType; // e.g., "DEPOSIT", "WITHDRAWAL"
    private LocalDateTime transactionDate;
}

