package org.programmers.VoucherManagement.wallet.exception;

public enum WalletExceptionMessage {
    FAIL_TO_INSERT("데이터가 정상적으로 저장되지 않았습니다."),
    FAIL_TO_DELETE("데이터가 정상적으로 삭제되지 않았습니다.");

    private final String message;

    WalletExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
