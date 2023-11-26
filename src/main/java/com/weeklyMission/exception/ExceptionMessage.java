package com.weeklyMission.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    BAD_REQUEST_VOUCHER("지원하지 않는 voucher"),
    NOT_FOUND_MEMBER("존재하지 않는 member"),
    NOT_FOUND_VOUCHER("존재하지 않는 voucher"),
    CAN_NOT_ZERO("0이 될 수 없음"),
    CAN_NOT_MINUS("음수는 될 수 없음"),
    ALREADY_JOIN("이미 가입된 이메일"),
    ALREADY_EXIST_WALLET("이미 지갑에 갖고 있습니다.");


    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
