package com.exercise.boot.repository;

import com.exercise.boot.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer> {
    List<Transfer> findByFromAccountId(int fromAccountId);

    List<Transfer> findByTransferType(String transferType);

    List<Transfer> findByTransferDate(LocalDate transferDate);

    List<Transfer> findByToAccountId(int toAccountId);

    List<Transfer> findByTransferDateBetween(LocalDate fromDate, LocalDate toDate);
}
