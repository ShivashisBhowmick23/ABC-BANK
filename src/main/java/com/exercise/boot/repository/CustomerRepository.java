package com.exercise.boot.repository;

import com.exercise.boot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c JOIN FETCH c.accountList a WHERE a.account_id = :accountId")
    Optional<Customer> findCustomerByAccountId(@Param("accountId") long accountId);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.cust_name) LIKE CONCAT(:letter, '%')")
    List<Customer> findByCustNameStartingWithIgnoreCase(char letter);
    @Query("UPDATE Customer c SET c.cust_name = :name WHERE c.cust_id = :id")
    String updateCustomerOnlyNameById(@Param("id") Long id, String name);

}

