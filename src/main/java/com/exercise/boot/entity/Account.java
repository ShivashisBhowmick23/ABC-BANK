package com.exercise.boot.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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


