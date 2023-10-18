package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {

    FIXED,
    PERCENT;

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    public static VoucherType of(String input){
        try{
            return VoucherType.valueOf(input.toUpperCase());
        }catch (RuntimeException e){
            logger.warn("Error Message = {}", e.getMessage());
            throw ErrorMessage.error("바우처 타입이 잘못되었습니다. 고정(Fixed) 할인 또는 비율(PERCENT) 할인 바우처 타입 중 값을 입력해주세요.INVALID_VOUCHER_TYPE.MSG");
        }
    }
}
