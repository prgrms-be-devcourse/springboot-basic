package com.zerozae.voucher.domain.customer;

import com.zerozae.voucher.exception.ExceptionMessage;


public enum CustomerType {

    NORMAL,
    BLACKLIST;

    public static CustomerType of(String input) {
        try {
            return CustomerType.valueOf(input.toUpperCase());
        }catch (RuntimeException e){
            throw ExceptionMessage.error("존재하지 않는 회원 타입입니다.");
        }
    }
}
