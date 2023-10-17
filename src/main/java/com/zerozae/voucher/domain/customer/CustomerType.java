package com.zerozae.voucher.domain.customer;

import com.zerozae.voucher.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.zerozae.voucher.common.message.MessageConverter.getMessage;

public enum CustomerType {
    NORMAL,
    BLACKLIST;

    private static final Logger logger = LoggerFactory.getLogger(CustomerType.class);

    public static CustomerType of(String customerType){
        try {
            return CustomerType.valueOf(customerType.toUpperCase());
        }catch (RuntimeException e){
            logger.error("Error Message = {}", e.getMessage());
            throw ErrorMessage.error(getMessage("INVALID_CUSTOMER_TYPE.MSG"));
        }
    }
}
