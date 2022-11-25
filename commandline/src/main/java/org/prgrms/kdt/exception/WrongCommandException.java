package org.prgrms.kdt.exception;

public class WrongCommandException extends CustomerException {
    public WrongCommandException() {
    }

    public WrongCommandException(String message) {
        super(message);
    }
}
