package com.prgrms.voucher_manage.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {
    EMPTY_INPUT_NOT_ALLOWED("빈 입력값은 허용되지 않습니다."),
    INVALID_COMMAND_INPUT("명령어를 올바르게 입력해주세요."),
    INVALID_UUID_FORMAT("UUID 형식이 올바르지 않습니다."),
    CUSTOMER_NOT_EXIST("찾으려는 회원이 존재하지 않습니다."),
    CUSTOMER_UPDATE_FAILED("회원 업데이트에 실패하였습니다"),
    BLACK_CUSTOMER_NOT_EXIST("찾으려는 블랙 회원이 존재하지 않습니다"),
    VOUCHER_UPDATE_FAILED("바우처 업데이트에 실패하였습니다."),
    VOUCHER_DELETE_FAILED("바우처 삭제에 실패하였습니다.");
    private final String message;
}
