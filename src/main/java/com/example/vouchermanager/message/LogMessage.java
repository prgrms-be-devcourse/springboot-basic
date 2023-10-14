package com.example.vouchermanager.message;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
public enum LogMessage {

    SELECT_FUNCTION("기능 선택"),
    SELECT_CREATE("create 기능 선택"),
    SELECT_LIST("list 기능 선택"),

    CREATE_START("create 기능 시작"),
    GET_VOUCHER_TYPE("voucher 종류 얻기"),
    GET_VOUCHER_DISCOUNT("voucher discount 정보 얻기"),
    VOUCHER_INFO("voucher 종류: {}, voucher 할인량/할인율: {}"),

    LIST_START("list 기능 시작"),
    VOUCHER_LIST_PRINT("voucher list 출력 시작"),

    NOT_CORRECT_COMMAND("적절하지 않은 명령어 입력 오류"),
    NOT_CORRECT_FORM("적절하지 않은 형식의 입력 오류"),
    NOT_CORRECT_SCOPE("적절하지 않은 percent 범위");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }
}
