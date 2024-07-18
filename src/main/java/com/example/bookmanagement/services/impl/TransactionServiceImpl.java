package com.example.bookmanagement.services.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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

    public TransactionServiceImpl(ITransactionRepository transactionRepository, ModelMapper modelMapper, RSAUtil rsaUtil) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.rsaUtil = rsaUtil;
    }

    @Override
    public Page<TransactionResponse> findAll(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return transactions.map(transaction -> {
            TransactionResponse response = modelMapper.map(transaction, TransactionResponse.class);
            return response;
        });
    }

    @Override
    public TransactionResponse get(Long id) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()) {
            TransactionResponse response = modelMapper.map(transaction.get(), TransactionResponse.class);
            response.setTransactionId(rsaUtil.decrypt(response.getTransactionId()));
            response.setAccount(rsaUtil.decrypt(response.getAccount()));
            // Decrypt other fields if needed
            return response;
        } else {
            throw new ResourceNotFoundException(Translator.toLocale(MessagesConstants.TRANSACTION_NOT_FOUND_ERROR) + id);
        }
    }

    @Override
    @Transactional
    public Long create(TransactionRequest transactionRequest) throws Exception {
        TransactionRequest encryptedRequest = encryptTransactionRequest(transactionRequest);
        Transaction transaction = modelMapper.map(encryptedRequest, Transaction.class);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return savedTransaction.getId();
    }

    @Override
    @Transactional
    public TransactionResponse update(Long id, TransactionRequest transactionRequest) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        TransactionRequest encryptedRequest = encryptTransactionRequest(transactionRequest);

        Optional<Transaction> existingTransaction = transactionRepository.findById(id);
        if (existingTransaction.isPresent()) {
            Transaction transaction = existingTransaction.get();
            modelMapper.map(encryptedRequest, transaction);
            transactionRepository.save(transaction);
            TransactionResponse response = modelMapper.map(transaction, TransactionResponse.class);
            response.setTransactionId(rsaUtil.decrypt(response.getTransactionId()));
            response.setAccount(rsaUtil.decrypt(response.getAccount()));
            return response;
        } else {
            throw new ResourceNotFoundException(Translator.toLocale(MessagesConstants.TRANSACTION_NOT_FOUND_ERROR) + id);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        Optional<Transaction> existingTransaction = transactionRepository.findById(id);
        if (existingTransaction.isPresent()) {
            transactionRepository.delete(existingTransaction.get());
        } else {
            throw new ResourceNotFoundException(Translator.toLocale(MessagesConstants.TRANSACTION_NOT_FOUND_ERROR) + id);
        }
    }

    public TransactionRequest encryptTransactionRequest(TransactionRequest request) throws Exception {
        TransactionRequest encryptedRequest = new TransactionRequest();
        encryptedRequest.setTransactionId(rsaUtil.encrypt(request.getTransactionId()));
        encryptedRequest.setAccount(rsaUtil.encrypt(request.getAccount()));
        return encryptedRequest;
    }

    public TransactionRequest decryptTransactionRequest(TransactionRequest encryptedRequest) throws Exception {
        encryptedRequest.setTransactionId(rsaUtil.decrypt(encryptedRequest.getTransactionId()));
        encryptedRequest.setAccount(rsaUtil.decrypt(encryptedRequest.getAccount()));
        return encryptedRequest;
    }
}