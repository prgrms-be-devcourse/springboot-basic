package org.prgrms.java.exception.notfound;

public class CommandNotFoundException extends NotFoundException {
    public CommandNotFoundException() {
        super("잘못된 명령어 입니다.");
    }

    public CommandNotFoundException(String message) {
        super(message);
    }
}
