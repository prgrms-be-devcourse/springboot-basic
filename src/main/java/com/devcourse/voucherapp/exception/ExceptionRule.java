package com.devcourse.voucherapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ExceptionRule {

    MENU_INVALID(HttpStatus.BAD_REQUEST, "잘못된 메뉴를 선택하셨습니다."),

    VOUCHER_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 ID에 해당하는 할인권이 없습니다."),
    VOUCHER_TYPE_INVALID(HttpStatus.BAD_REQUEST, "잘못된 할인권 방식을 선택하셨습니다."),
    VOUCHER_DISCOUNT_AMOUNT_INVALID(HttpStatus.BAD_REQUEST, "입력하신 수치가 해당 할인권 방식의 조건에 맞지 않습니다."),

    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "입력하신 닉네임에 해당하는 고객이 없습니다."),
    CUSTOMER_NICKNAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 고객 닉네임입니다."),
    CUSTOMER_TYPE_INVALID(HttpStatus.BAD_REQUEST, "잘못된 고객 타입을 선택하셨습니다."),
    CUSTOMER_NICKNAME_INVALID(HttpStatus.BAD_REQUEST, "입력하신 닉네임이 조건(공백이 없는 소문자 알파벳과 숫자 조합)에 맞지 않습니다."),

    NOT_ALLOWED_HTTP_METHOD(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 요청입니다."),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러가 발생했습니다. 이용에 불편함을 드려 죄송합니다.");

    private final HttpStatus status;
    private final String message;
}
