package com.programmers.voucher.exception;

public enum VoucherErrorCode {
    NOT_SUPPORTED_VOUCHER_TYPE("지원하지 않는 바우처입니다."),
    INVALID_VOUCHER_CREATION_REQUEST("올바르지 않은 바우처 생성 요청입니다."),
    MAXIMUM_DISCOUNT_AMOUNT_EXCEEDS("바우처의 최대 할인 양을 초과하였습니다.");

    private String errorMessage;

    VoucherErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
