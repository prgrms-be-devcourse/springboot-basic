package com.prgrms.io;

public enum Message {
    START("=== Voucher Program === \n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers."),
    ERROR("잘못된 형태로 입력하였습니다. 다시 시작합니다."),
    END("바우처 어플리케이션을 종료합니다."),
    COMPLETE_CREATE("바우처가 등록되었습니다."),
    EMPTY_REPOSITORY("만들어진 바우처 정책이 없습니다.");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}