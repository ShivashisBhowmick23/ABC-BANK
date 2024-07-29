package com.exercise.boot.entity;


import lombok.Data;


import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cust_id;
    private String cust_name;
    private boolean verification_documents;
    private String cust_mail;

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "cust_id")
    private List<Account> accountList;

    // Getters and Setters
}
