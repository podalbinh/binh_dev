package com.example.bookmanagement.repositories;


import org.springframework.data.jpa.repository.JpaRepository;



import com.example.bookmanagement.entities.Transaction;


/**
 * Repository interface for managing Transaction entities.
 */
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

}
