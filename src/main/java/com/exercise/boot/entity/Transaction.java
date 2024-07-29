package com.exercise.boot.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate transactionDate;
}

