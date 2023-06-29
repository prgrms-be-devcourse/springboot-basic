package org.prgrms.kdt.exception;

import org.prgrms.kdt.util.ErrorMessage;

public class DatabaseException extends RuntimeException {
    public DatabaseException() {
    }

    public DatabaseException(ErrorMessage message) {
        super(message.getMassage());
    }

    public DatabaseException(ErrorMessage message, Throwable cause) {
        super(message.getMassage(), cause);
    }
}
