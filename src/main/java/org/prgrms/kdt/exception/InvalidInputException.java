package org.prgrms.kdt.exception;

import org.prgrms.kdt.util.ErrorMessage;

// exception /  error message
public class InvalidInputException extends RuntimeException{
    public InvalidInputException() {
    }

    public InvalidInputException(ErrorMessage message) {
        super(message.getMassage());
    }

    public InvalidInputException(ErrorMessage message, Throwable cause) {
        super(message.getMassage(), cause);
    }
}
