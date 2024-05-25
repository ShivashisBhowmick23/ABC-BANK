package com.exercise.boot.repository;

import com.exercise.boot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c JOIN c.accountList a WHERE a.account_id = :accountId")
    Optional<Customer> findCustomerByAccountId(@Param("accountId") long accountId);
}

