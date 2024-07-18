package com.example.bookmanagement.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tomcat.util.json.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookmanagement.configs.Translator;
import com.example.bookmanagement.dto.request.TransactionRequest;
import com.example.bookmanagement.dto.response.TransactionResponse;
import com.example.bookmanagement.entities.Transaction;
import com.example.bookmanagement.exception.ResourceNotFoundException;
import com.example.bookmanagement.repositories.ITransactionRepository;
import com.example.bookmanagement.services.ITransactionService;
import com.example.bookmanagement.utils.Constants;
import com.example.bookmanagement.utils.MessagesConstants;
import com.example.bookmanagement.utils.RSAUtil;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final RSAUtil rsaUtil;

    public TransactionServiceImpl(ITransactionRepository transactionRepository, ModelMapper modelMapper,RSAUtil rsaUtil) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.rsaUtil=rsaUtil;
    }


    /**
     * Retrieve all transactions with pagination.
     *
    //  * @param pageable pagination information
     * @return a page of transactionResponse objects
     */
    @Override
    public Page<TransactionResponse> findAll(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.map(transaction -> modelMapper.map(transaction, TransactionResponse.class));
    }

    /**
     * Retrieve an transaction by ID.
     *
     * @param id the ID of the transaction to retrieve
     * @return the transactionResponse object corresponding to the ID
     * @throws IllegalArgumentException if no transaction is found with the given ID
     */
    @Override
    public TransactionResponse get(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(null);
        if (transaction.isPresent()) {
            return modelMapper.map(transaction.get(), TransactionResponse.class);
        } else {
            throw new ResourceNotFoundException(Translator.toLocale(MessagesConstants.TRANSACTION_NOT_FOUND_ERROR)+id);
        }
    }

    /**
     * Create a new transaction.
     *
     * @param transactionRequest the transactionRequest object containing transaction information
     * @return the ID of the created transaction
     */
    @Override
    @Transactional
    public Long create(TransactionRequest transactionRequest) {
        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
        Transaction savedtransaction = transactionRepository.save(transaction);
        return savedtransaction.getId();
    }

    /**
     * Update an existing transaction.
     *
     * @param id            the ID of the transaction to update
     * @param transactionRequest the transactionRequest object containing updated transaction information
     * @throws IllegalArgumentException if no transaction is found with the given ID
     */
    @Override
    @Transactional
    public void update(Long id, TransactionRequest transactionRequest) {
        Optional<Transaction> existingtransaction = transactionRepository.findById(id);
        if (existingtransaction.isPresent()) {
            Transaction transaction = existingtransaction.get();
            modelMapper.map(transactionRequest, transaction);
            transactionRepository.save(transaction);
        } else {
            throw new ResourceNotFoundException(Translator.toLocale(MessagesConstants.TRANSACTION_NOT_FOUND_ERROR)+id);
        }
    }

    /**
     * Delete an transaction by ID.
     *
     * @param id the ID of the transaction to delete
     * @throws IllegalArgumentException if no transaction is found with the given ID
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Transaction> existingtransaction = transactionRepository.findById(id);
        if (existingtransaction.isPresent()) {
            transactionRepository.delete(existingtransaction.get());
        } else {
            throw new ResourceNotFoundException(Translator.toLocale(MessagesConstants.TRANSACTION_NOT_FOUND_ERROR)+id);
        }
    }


    public TransactionRequest encryptTransactionRequest(TransactionRequest request) throws Exception {
        TransactionRequest encryptedRequest = new TransactionRequest();
        encryptedRequest.setId(request.getId()); // Copy non-sensitive fields directly

        // Encrypt sensitive fields
        encryptedRequest.setTransactionId(rsaUtil.encrypt(request.getTransactionId()));
        encryptedRequest.setAccount(rsaUtil.encrypt(request.getAccount()));
        encryptedRequest.setInDebt(rsaUtil.encrypt(request.getInDebt().toString()));
        encryptedRequest.setHave(rsaUtil.encrypt(request.getHave().toString()));
        encryptedRequest.setTime(rsaUtil.encrypt(dateToString(request.getTime())));

        return encryptedRequest;
    }

    public TransactionRequest decryptTransactionRequest(TransactionRequest encryptedRequest) throws Exception {
        // Decrypt sensitive fields
        encryptedRequest.setTransactionId(rsaUtil.decrypt(encryptedRequest.getTransactionId()));
        encryptedRequest.setAccount(rsaUtil.decrypt(encryptedRequest.getAccount()));
        encryptedRequest.setInDebt(Double.parseDouble(rsaUtil.decrypt(encryptedRequest.getInDebt())));
        encryptedRequest.setHave(Double.parseDouble(rsaUtil.decrypt(encryptedRequest.getHave())));
        encryptedRequest.setTime(stringToDate(rsaUtil.decrypt(encryptedRequest.getTime())));

        return encryptedRequest;
    }

    // Utility method to convert Date to String
    private String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    // Utility method to convert String to Date
    private Date stringToDate(String dateString) throws ParseException, java.text.ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(dateString);
    }
}
