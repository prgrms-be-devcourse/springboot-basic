package org.prgrms.kdt.exception;

public enum ErrorMessage {
    INVALID_TYPE("K001","해당 바우처는 발급 불가능합니다"),
    INPUT_ERROR("K002","입력이 잘못되었습니다."),
    INSERT_FAILED("K003","추가된 데이터 내역이 없습니다."),
    NOT_FOUND_VOUCHER("K004","해당하는 바우처를 찾을 수 없습니다."),
    INVALID_DISCOUNT_AMOUNT("K005","적합한 할인 금액이 아닙니다.");

    private String errorCode;
    private String errorString;

    ErrorMessage(String errorCode, String errorString) {
        this.errorCode = errorCode;
        this.errorString = errorString;
    }
}
