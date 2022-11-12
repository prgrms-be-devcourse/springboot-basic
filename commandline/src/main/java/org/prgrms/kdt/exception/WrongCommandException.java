package org.prgrms.kdt.exception;

public class WrongCommandException extends RuntimeException {
    public WrongCommandException() {
    }

    public WrongCommandException(String message) {
        super(message);
    }
}
