package org.prgrms.kdt.utils;

public enum MessageType {
    CONSOLE_START("""
            === Voucher Program ===
            Type 'exit' to exit the program.
            Type 'create' to create a new voucher.
            Type 'list' to list all vouchers."""),
    SELECT_WRONG("올바른 입력이 아닙니다. 보기 중에 입력해주세요."),
    CANCEL("취소되었습니다."),
    CONSOLE_END("Exit Program. Good Bye.");

    private final String message;

    MessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
