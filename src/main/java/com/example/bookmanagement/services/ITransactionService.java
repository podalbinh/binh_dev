package com.example.bookmanagement.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bookmanagement.dto.request.TransactionRequest;
import com.example.bookmanagement.dto.response.TransactionResponse;

public interface ITransactionService {

    /**
     * Retrieve all transactions with pagination.
     *
     * @param pageable pagination information
     * @return a page of transaction responses
     */
    public Page<TransactionResponse> findAll(Pageable pageable);

    /**
     * Retrieve an transaction by ID.
     *
     * @param id the ID of the transaction to retrieve
     * @return the transaction response
     */
    public TransactionResponse get(final Long id);

    /**
     * Create a new transaction.
     *
     * @param transactionRequest the request object containing transaction details
     * @return the ID of the created transaction
     */
    public Long create(final TransactionRequest transactionRequest);

    /**
     * Update an existing transaction.
     *
     * @param id the ID of the transaction to update
     * @param transactionRequest the request object containing updated transaction details
     */
    public void update(final Long id, final TransactionRequest transactionRequest);

    /**
     * Delete an transaction by ID.
     *
     * @param id the ID of the transaction to delete
     */
    public void delete(final Long id);
}
