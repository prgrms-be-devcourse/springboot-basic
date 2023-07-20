package com.prgrms.spring.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Error {
    VALIDATION_WRONG_TYPE(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    CREATE_VOUCHER_EXCEPTION(HttpStatus.NOT_FOUND, "바우처 등록에 실패하였습니다."),
    CREATE_CUSTOMER_EXCEPTION(HttpStatus.NOT_FOUND, "고객 등록에 실패하였습니다."),
    NOT_FOUND_CUSTOMER_EXCEPTION(HttpStatus.NOT_FOUND, "유저 조회 실패"),
    NOT_FOUND_VOUCHER_EXCEPTION(HttpStatus.NOT_FOUND, "바우처 조회 실패"),
    VALIDATION_REQUEST_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청값이 입력되지 않았습니다."),
    VALIDATION_REQUEST_HEADER_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청 헤더값이 입력되지 않았습니다."),
    VALIDATION_REQUEST_PARAMETER_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청 파라미터값이 입력되지 않았습니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
