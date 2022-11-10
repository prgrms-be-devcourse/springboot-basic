package org.prgrms.kdt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    IS_NOT_NUMBER(400, "숫자가 아닙니다."),
    NOT_FIND_VOUCHER_TYPE(400, "잘못된 voucher 타입을 입력하셨습니다."),
    WRONG_RANGE_INPUT(400, "유효한 범위를 벗어났습니다."),
    WRONG_COMMAND(400, "잘못된 명령어입니다."),
    IOEXCEPTION(400, "잘못된 입력입니다.");

    private final int status;
    private final String message;
}
