package org.weekly.weekly.util;

import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR]: "),
    NOT_ACCESS(HttpStatus.FORBIDDEN, "잘못된 접근"),
    UTIL_CLASS(HttpStatus.BAD_REQUEST, "유틸 클래스입니다."),
    EMPTY(HttpStatus.BAD_REQUEST, "사용자가 아무 값도 입력하지 않았습니다."),
    NOT_INPUT_FORMAT(HttpStatus.BAD_REQUEST, "입력 형식에 맞지 않습니다."),
    NOT_MENU(HttpStatus.NOT_FOUND, "해당 메뉴는 존재하지 않습니다."),
    NOT_NUMBER_FORMAT(HttpStatus.BAD_REQUEST, "허용되지 않는 값입니다."),
    NOT_NATURAL_NUMBER(HttpStatus.BAD_REQUEST, "자연수가 아닙니다."),
    NOT_DISCOUNT(HttpStatus.BAD_REQUEST, "할인 종류가 아닙니다."),
    EXPIRATION_ERROR(HttpStatus.BAD_REQUEST, "유효기간이 등록기간보다 빠릅니다."),
    VOUCHER_EXIST(HttpStatus.CONFLICT, "이미 존재하는 바우처입니다."),
    NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 종류의 할인정보를 찾을 수 없습니다."),
    NOT_SAME_PARAM_SIZE(HttpStatus.BAD_REQUEST,"입력 파라미터 개수가 맞지 않습니다."),
    NOT_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일형식이 아닙니다."),
    SQL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"값을 가져오기 실패"),
    SQL_INSERT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "값 추가 실패"),
    SQL_EXIST(HttpStatus.CONFLICT, "이미 존재합니다."),
    SQL_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "삭제 실패");

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getMessage() {
        return ERROR.message + message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
