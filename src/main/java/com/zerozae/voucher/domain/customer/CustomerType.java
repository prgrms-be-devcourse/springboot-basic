package com.zerozae.voucher.domain.customer;

import com.zerozae.voucher.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public enum CustomerType {

    NORMAL,
    BLACKLIST;

    private static final Logger logger = LoggerFactory.getLogger(CustomerType.class);

    public static CustomerType of(String input){
        try {
            return CustomerType.valueOf(input.toUpperCase());
        }catch (RuntimeException e){
            logger.warn("Error Message = {}", e.getMessage());
            throw ErrorMessage.error("존재하지 않는 회원 타입입니다.");
        }
    }
}
