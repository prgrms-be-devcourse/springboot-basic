package org.prgrms.kdt.error;

public enum ErrorMessage {
    NOTFOUND_MENU("입력하신 메뉴가 존재하지 않습니다."),
    NOTFOUND_VOUCHER("입력하신 바우처가 존재하지 않습니다."),
    INPUT_COUNT_ERROR("입력 개수가 올바르지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
