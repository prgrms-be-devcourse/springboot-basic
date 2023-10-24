package com.zerozae.voucher.domain.customer;

import com.zerozae.voucher.exception.ErrorMessage;


public enum CustomerType {

    NORMAL,
    BLACKLIST;

    public static CustomerType of(String input){
        try {
            return CustomerType.valueOf(input.toUpperCase());
        }catch (RuntimeException e){
            throw ErrorMessage.error("존재하지 않는 회원 타입입니다.");
        }
    }
}
