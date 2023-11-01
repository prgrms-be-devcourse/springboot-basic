package com.programmers.springbootbasic.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    FILE_IO_ERROR("파일 에러입니다. 시스템을 종료합니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR("데이터베이스 에러입니다. 저장에 실패하였습니다. 다시 시도하세요.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_MENU("올바르지 않은 메뉴 타입입니다.", HttpStatus.BAD_REQUEST),
    INVALID_VOUCHER("올바르지 않은 바우처 타입 입니다. 현재 등록 가능한 바우처 타입은 FIXED, PERCENT 입니다.", HttpStatus.BAD_REQUEST),
    INVALID_FIXED_VOUCHER_BENEFIT("고정 할인 금액은 0원 이상이어야 합니다.", HttpStatus.BAD_REQUEST),
    INVALID_PERCENT_VOUCHER_BENEFIT("비율 할인 금액은 0% 이상 100% 이하여야 합니다.", HttpStatus.BAD_REQUEST),
    INVALID_FILE_PATH("올바르지 않은 파일 경로입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    EXIT("시스템을 종료합니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND_VOUCHER("바우처를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_USER("사용자를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_USER_VOUCHER("사용자 바우처를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    FAIL_TO_DELETE_VOUCHER("바우처 삭제에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FAIL_TO_UPDATE_VOUCHER("바우처 수정에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DUPLICATED_VOUCHER("이미 등록된 바우처 입니다.", HttpStatus.CONFLICT),
    DUPLICATED_USER("이미 등록된 사용자 입니다.", HttpStatus.CONFLICT),
    FAIL_TO_DELETE_USER_VOUCHER("사용자 바우처 삭제에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final String message;
    private final HttpStatus httpStatus;

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
