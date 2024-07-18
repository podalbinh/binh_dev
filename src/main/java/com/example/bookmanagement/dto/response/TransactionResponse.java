package com.example.bookmanagement.dto.response;

import java.util.Date;

import lombok.Data;
@Data
public class TransactionResponse {
    private Long id;
    private String transactionId;
    private String account;
    private Double inDebt;
    private Double have;
    private Date time;
}
