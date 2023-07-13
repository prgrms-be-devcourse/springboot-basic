package com.prgrms.presentation.message;

public enum GuideMessage {
    START("=== Voucher Program === \n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers."),
    CLOSE("바우처 어플리케이션을 종료합니다."),
    COMPLETE_CREATE("바우처가 등록되었습니다.");

    private final String message;

    GuideMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
