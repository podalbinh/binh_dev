package com.example.bookmanagement.security;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bookmanagement.utils.RSAUtil;

@Aspect
@Component
public class EncryptionAspect {

    private final RSAUtil rsaUtil;

    @Autowired
    public EncryptionAspect(RSAUtil rsaUtil) {
        this.rsaUtil = rsaUtil;
    }

    @Before("execution(* com.example.bookmanagement.service.*.*(..)) && args(transactionID, account, inDebt, have, time, ..)")
    public void encryptParameters(String transactionID, String account, String inDebt, String have, String time) throws Exception {
        transactionID = rsaUtil.encrypt(transactionID);
        account = rsaUtil.encrypt(account);
        inDebt = rsaUtil.encrypt(inDebt);
        have = rsaUtil.encrypt(have);
        time = rsaUtil.encrypt(time);
    }
}
