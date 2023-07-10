package org.programmers.VoucherManagement.voucher.exception;

public enum VoucherExceptionMessage {
    NOT_INCLUDE_1_TO_100("할인율은 1부터 100사이의 값이여야 합니다."),
    AMOUNT_IS_NOT_NUMBER("숫자만 입력가능합니다."),
    NOT_EXIST_COMMAND("해당하는 Command가 존재하지 않습니다."),
    NOT_EXIST_DISCOUNT_TYPE("해당하는 유형의 바우처가 존재하지 않습니다."),
    NOT_FOUND_VOUCHER("바우처를 찾을 수 없습니다."),
    FAIL_TO_INSERT("데이터가 정상적으로 저장되지 않았습니다."),
    FAIL_TO_UPDATE("데이터가 정상적으로 수정되지 않았습니다."),
    FAIL_TO_DELETE("데이터가 정상적으로 삭제되지 않았습니다.");

    private final String message;

    VoucherExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
