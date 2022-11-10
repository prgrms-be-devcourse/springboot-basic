package org.prgrms.kdt.exception;

public class WrongCommand extends RuntimeException {
    public WrongCommand() {
    }

    public WrongCommand(String message) {
        super(message);
    }
}
