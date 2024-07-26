package com.exercise.boot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
