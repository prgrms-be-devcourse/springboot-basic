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
            {}""");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }
}
