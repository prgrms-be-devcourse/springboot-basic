package com.devcourse.voucherapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionRule {

    MENU_INVALID("잘못된 메뉴를 선택하셨습니다."),
    VOUCHER_NOT_FOUND("입력하신 ID에 해당하는 할인권이 없습니다."),
    VOUCHER_TYPE_INVALID("잘못된 할인권 방식을 선택하셨습니다."),
    VOUCHER_DISCOUNT_AMOUNT_INVALID("입력하신 수치가 해당 할인권 방식의 조건에 맞지 않습니다."),
    CUSTOMER_NOT_FOUND("입력하신 닉네임에 해당하는 고객이 없습니다."),
    CUSTOMER_NICKNAME_ALREADY_EXIST("이미 존재하는 고객 닉네임입니다."),
    CUSTOMER_TYPE_INVALID("잘못된 고객 타입을 선택하셨습니다."),
    CUSTOMER_NICKNAME_INVALID("입력하신 닉네임이 조건(공백이 없는 소문자 알파벳과 숫자 조합)에 맞지 않습니다.");

    private final String message;
}
