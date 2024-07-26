package com.exercise.boot.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
public class Account {

    @Id
    @Column(name = "account_id", unique = true, nullable = false)
    private Long account_id;
    private String account_type;
    private double balance;

    // Getters and Setters
}


