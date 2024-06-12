package com.exercise.boot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transferId;
    private int fromAccountId;
    private int toAccountId;
    private double amount;
    private String transferType;
    private LocalDate transferDate;
}
