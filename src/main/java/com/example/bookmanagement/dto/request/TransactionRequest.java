package com.example.bookmanagement.dto.request;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionRequest {
    private Long id;
    private String transactionId;
    private String account;
    private String inDebt;
    private String have;
    private String time;
}
