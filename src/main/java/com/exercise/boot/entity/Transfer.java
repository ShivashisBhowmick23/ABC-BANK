package com.exercise.boot.entity;

import lombok.Data;

import javax.persistence.*;
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
