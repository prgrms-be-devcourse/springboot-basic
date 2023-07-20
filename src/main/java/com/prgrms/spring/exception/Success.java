package com.prgrms.spring.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Success {
    CREATE_VOUCHER_SUCCESS(HttpStatus.CREATED, "바우처가 성공적으로 등록되었습니다."),
    CREATE_CUSTOMER_SUCCESS(HttpStatus.CREATED, "고객이 성공적으로 등록되었습니다."),
    GET_VOUCHER_SUCCESS(HttpStatus.OK, "바우처 조회 성공"),
    DELETE_VOUCHER_SUCCESS(HttpStatus.OK, "바우처 삭제 성공"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
