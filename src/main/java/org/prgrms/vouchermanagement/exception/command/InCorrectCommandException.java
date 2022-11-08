package org.prgrms.vouchermanagement.exception.command;

public class InCorrectCommandException extends RuntimeException {
    public InCorrectCommandException() {
        super("올바르지 않은 명령어입니다.");
    }
}
