package com.prgrms.vouchermanager.message;

import lombok.Getter;

@Getter
public enum LogMessage {

    FINISH_PROGRAM("프로그램 종료"),

    CHECK_VOUCHER_REPOSITORY("voucher 리포지토리 모드: {}"),
    CHECK_CUSTOMER_REPOSITORY("customer 리포지토리 모드: {}"),

    SELECT_CREATE("create 기능 선택"),
    SELECT_LIST("list 기능 선택"),
    SELECT_BLACKLIST("blacklist 기능 선택"),

    VOUCHER_TYPE_AND_DISCOUNT("voucher 타입: {}, 할인 정보: {}"),
    VOUCHER_INFO("""
            Voucher 정보 :
            {}"""),

    NOT_CORRECT_COMMAND("적절하지 않은 명령어 입력 오류"),
    NOT_CORRECT_FORM("적절하지 않은 형식의 입력 오류"),
    NOT_CORRECT_SCOPE("적절하지 않은 percent 범위");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }
}
