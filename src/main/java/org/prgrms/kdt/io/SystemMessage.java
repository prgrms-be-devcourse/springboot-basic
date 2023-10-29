package org.prgrms.kdt.io;

public enum SystemMessage {
    EXIT_PROGRAM("프로그램을 종료합니다.");

    private final String message;

    public String getMessage() {
        return message;
    }

    SystemMessage(String message) {
        this.message = message;
    }
}
