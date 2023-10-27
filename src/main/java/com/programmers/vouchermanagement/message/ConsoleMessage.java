package com.programmers.vouchermanagement.message;

public enum ConsoleMessage {
    MAIN_COMMAND_LIST_MESSAGE("""
            === Voucher Program ===
            0. 프로그램 종료
            1. 바우처 관리 메뉴
            2. 고객 관리 메뉴
            3. 지갑(고객-바우처) 관리 메뉴
            """),
    WELCOME_MESSAGE("[System] Welcome to voucher management program."),
    EXIT_MESSAGE("[System] Program exited.");

    private final String message;

    ConsoleMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
