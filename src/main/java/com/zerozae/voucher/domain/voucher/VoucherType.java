package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.common.message.MessageConverter;
import com.zerozae.voucher.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {
    FIXED,
    PERCENT;

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    public static VoucherType of(String voucherType){
        try{
            return VoucherType.valueOf(voucherType.toUpperCase());
        }catch (RuntimeException e){
            logger.error("Error Message = {}", e.getMessage());
            throw ErrorMessage.error(MessageConverter.getMessage("INVALID_VOUCHER_TYPE.MSG"));
        }
    }
}
