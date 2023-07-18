package org.prgrms.kdt.exception;

public enum ErrorMessage {
    INVALID_TYPE("해당 바우처는 발급 불가능합니다"),
    INPUT_ERROR("입력이 잘못되었습니다."),
    INSERT_FAILED("추가된 데이터 내역이 없습니다."),
    NOT_FOUND_VOUCHER("해당하는 바우처를 찾을 수 없습니다."),
    INVALID_DISCOUNT_AMOUNT("적합한 할인 금액이 아닙니다.");

    private String eroorString;

    ErrorMessage(String errorString) {
        this.eroorString = errorString;
    }
}
