package org.prgrms.kdt.io;

public enum SystemMessage {
    EXIT_PROGRAM("프로그램을 종료합니다."),
    EXCEPTION_INPUT("입력값이 잘못되었습니다."),
    EXCEPTION_NOT_EXIST_MENU("올바른 메뉴를 입력해주세요.");

    private final String message;

    public String getMessage() {
        return message;
    }

    SystemMessage(String message) {
        this.message = message;
    }
}
