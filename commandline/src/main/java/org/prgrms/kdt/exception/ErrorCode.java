package org.prgrms.kdt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    IS_NOT_NUMBER_EXCEPTION("숫자가 아닙니다."),
    NOT_FOUND_VOUCHER_TYPE_EXCEPTION("잘못된 voucher 타입을 입력하셨습니다."),
    WRONG_RANGE_INPUT_EXCEPTION("유효한 범위를 벗어났습니다."),
    WRONG_COMMAND_EXCEPTION("잘못된 명령어입니다."),
    INPUT_EXCEPTION("잘못된 입력입니다."),
    FILE_NOT_FOUND_EXCEPTION("파일을 찾을 수 없습니다."),
    FILE_INOUT_EXCEPTION("파일 입출력 오류입니다."),
    NOT_FOUND_VOUCHER_EXCEPTION("입력하신 voucher를 찾을 수 없습니다."),
    NOT_FOUND_VALUE_EXCEPTION("입력하신 key값에 해당하는 value값을 찾을 수 없습니다."),
    NOT_NULL_NAME_EXCEPTION("이름은 공백이 될 수 없습니다."),
    NOT_FOUND_CUSTOMER_EXCEPTION("해당하는 customer를 찾을 수 없습니다.");

    private final String message;
}
