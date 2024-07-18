package com.example.bookmanagement.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionRequest {
    private Long id;
    private String transactionId;
    private String account;
    private Double inDebt;
    private Double have;
    private Date time;
}
