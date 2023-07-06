package com.prgrms.spring.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Error {
    VALIDATION_WRONG_TYPE("잘못된 입력값입니다."),
    CREATE_VOUCHER_EXCEPTION("바우처 등록에 실패하였습니다."),
    CREATE_CUSTOMER_EXCEPTION("고객 등록에 실패하였습니다."),
    ;

    private final String message;
}
