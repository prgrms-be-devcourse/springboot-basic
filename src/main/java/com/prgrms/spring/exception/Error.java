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
    NOT_FOUND_CUSTOMER_EXCEPTION("유저 조회 실패"),
    NOT_FOUND_VOUCHER_EXCEPTION("바우처 조회 실패")
    ;

    private final String message;
}
