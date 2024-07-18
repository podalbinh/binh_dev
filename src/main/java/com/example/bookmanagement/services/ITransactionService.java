package com.example.bookmanagement.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bookmanagement.dto.request.TransactionRequest;
import com.example.bookmanagement.dto.response.TransactionResponse;

public interface ITransactionService {

    /**
     * Retrieve all authors with pagination.
     *
     * @param pageable pagination information
     * @return a page of author responses
     */
    public Page<TransactionResponse> findAll(Pageable pageable);

    /**
     * Retrieve an author by ID.
     *
     * @param id the ID of the author to retrieve
     * @return the author response
     */
    public TransactionResponse get(final Long id);

    /**
     * Create a new author.
     *
     * @param authorRequest the request object containing author details
     * @return the ID of the created author
     */
    public Long create(final TransactionRequest authorRequest);

    /**
     * Update an existing author.
     *
     * @param id the ID of the author to update
     * @param authorRequest the request object containing updated author details
     */
    public void update(final Long id, final TransactionRequest authorRequest);

    /**
     * Delete an author by ID.
     *
     * @param id the ID of the author to delete
     */
    public void delete(final Long id);
}
