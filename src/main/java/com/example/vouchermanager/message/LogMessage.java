package com.example.vouchermanager.message;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
public enum LogMessage {

    SELECT_FUNCTION("기능 선택"),
    SELECT_CREATE("create 기능 선택"),
    SELECT_LIST("list 기능 선택"),

    CREATE_START("create 모드 시작"),
    GET_VOUCHER_INFO("voucher 정보 얻기"),
    VOUCHER_TYPE_INFO("voucher 종류: {}"),
    VOUCHER_PRICE_INFO("voucher 기본 가격: {}"),

    VOUCHER_LIST_PRINT("voucher list 출력 시작"),
    VOUCHER_INFO("voucher 정보 : {}"),

    NOT_CORRECT_COMMAND("적절하지 않은 명령어 입력 오류"),
    NOT_CORRECT_FORM("적절하지 않은 형식의 입력 오류");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }
}
