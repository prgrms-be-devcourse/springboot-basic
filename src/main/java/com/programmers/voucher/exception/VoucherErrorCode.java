package com.programmers.voucher.exception;

public enum VoucherErrorCode {
    NOT_SUPPORTED_TYPE("지원하지 않는 바우처입니다.");

    private String errorMessage;

    VoucherErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
