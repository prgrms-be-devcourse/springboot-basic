package org.prgrms.springbootbasic.exception;

public enum ServiceExceptionMessage {

    ALREADY_ASSIGNED_VOUCHER_EXP_MSG("이미 할당된 바우처입니다."),
    AMOUNT_MAX_RANGE_EXP_MSG("amount는 100000보다 작아야 합니다."),
    AMOUNT_MIN_RANGE_EXP_MSG("amount는 0보다 작거나 같을 수 없습니다."),
    DUPLICATE_EMAIL_EXP_MSG("이메일이 중복되었습니다."),
    INVALID_UUID_FORMAT_EXP_MSG("UUID 포멧이 아닙니다."),
    INVALID_CUSTOMER_ID_EXP_MSG("해당 아이디의 Customer가 존재하지 않습니다."),
    INVALID_VOUCHER_ID_EXP_MSG("해당 아이디의 Voucher가 존재하지 않습니다."),
    PERCENT_MAX_RANGE_EXP_MSG("percent는 100보다 작아야합니다."),
    PERCENT_MIN_RANGE_EXP_MSG("percent는 0보다 커야합니다."),
    INVALID_EMAIL_FORMAT_EXP_MSG("이메일 형식이 틀렸습니다."),
    INVALID_NAME_FORMAT_EXP_MSG("이름 형식이 틀렸습니다."),
    INVALID_MENU_EXP_MSG("존재하지 않는 메뉴입니다."),
    INVALID_VOUCHER_TYPE_EXP_MSG("존재하지 않는 바우처 타입입니다.");

    private final String message;

    ServiceExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
