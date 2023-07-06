package com.prgrms.spring.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Success {
    CREATE_VOUCHER_SUCCESS("바우처가 성공적으로 등록되었습니다."),
    CREATE_CUSTOMER_SUCCESS("고객이 성공적으로 등록되었습니다."),
    ;

    private final String message;
}
