package com.prgrms.voucher_manager.io;

public enum Message {
    CONSOLE_INPUT("=== Voucher Program === \n" +
            "Type exit to exit the program.\n" +
            "Type create to create a new voucher.\n" +
            "Type list to list all vouchers."),
    CREATE_VOUCHER("\n=== Create Voucher ===\n" +
            "Type 1 to create FixedAmountVoucher \n" +
            "Type 2 to create PercentAmountVoucher"),
    WRONG_INPUT("잘못된 입력 값입니다. 다시 입력해 주세요."),
    EMPTY_REPOSITORY("등록된 Voucher 정보가 없습니다."),
    EXIT_PROGRAM("프로그램을 종료합니다.");


    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
