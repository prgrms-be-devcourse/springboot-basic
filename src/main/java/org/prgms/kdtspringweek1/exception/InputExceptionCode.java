package org.prgms.kdtspringweek1.exception;

public enum InputExceptionCode {
    INVALID_VOUCHER_FUNCTION_TYPE("[System] 지원 가능한 기능을 올바르게 입력해주세요."),
    INVALID_VOUCHER_TYPE("[System] 생성 가능한 바우처 타입을 입력해주세요."),
    INVALID_PERCENT_DISCOUNT("[System] 할인율은 0초과 100이하의 값으로 입력해주세요."),
    INVALID_FIXED_AMOUNT("[System] 할인액은 양수로 입력해주세요."),
    INVALID_DISCOUNT_VALUE("[System] 할인값은 양수로 입력해주세요.");

    private String message;

    InputExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
