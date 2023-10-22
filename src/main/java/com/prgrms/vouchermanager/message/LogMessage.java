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

    EMPTY_LIST_EXCEPTION("%s는 빈 리스트입니다."),
    FILE_IO_EXCEPTION("%s 객체에서 파일 입출력에 실패했습니다."),
    NOT_CORRECT_COMMAND("%s는 올바른 명령어가 아닙니다."),
    NOT_CORRECT_FORM("%s 의 형식이 올바르지 않습니다."),
    NOT_CORRECT_SCOPE("{}은 적절한 범위 안의 숫자가 아닙니다.");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }
}
