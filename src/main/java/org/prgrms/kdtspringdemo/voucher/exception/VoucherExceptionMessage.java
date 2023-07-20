package org.prgrms.kdtspringdemo.voucher.exception;

public enum VoucherExceptionMessage {
    OUT_OF_RANGE_AMOUNT("할인 범위가 아닙니다."),
    NOT_FOUND_VOUCHER_COMMAND_TYPE("알맞는 바우처 기능이 없습니다."),
    NOT_FOUND_VOUCHER_TYPE("알맞는 바우처 할인 유형이 없습니다."),
    VOUCHER_ID_LOOKUP_FAILED("조회된 바우처 ID가 없습니다.")
    ;

    private String message;

    VoucherExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] : " + message;
    }
}
