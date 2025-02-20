package com.zilaidawwab.fintechapp.repository;

import com.zilaidawwab.fintechapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

// Database "Transaction" whose primary key is a "String"
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    //
}
