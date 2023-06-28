package org.programmers.VoucherManagement.voucher.exception;

public enum VoucherExceptionMessage {
    NOT_INCLUDE_1_TO_100("할인율은 1부터 100사이의 값이여야 합니다."),
    VOUCHER_AMOUNT_IS_NOT_NUMBER("할인 금액은 숫자만 입력 가능합니다."),
    NOT_EXIST_COMMAND("해당하는 Command가 존재하지 않습니다."),
    NOT_EXIST_DISCOUNT_TYPE("해당하는 유형의 바우처가 존재하지 않습니다.");

    private final String message;

    VoucherExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
