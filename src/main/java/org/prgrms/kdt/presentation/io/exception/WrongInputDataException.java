package org.prgrms.kdt.presentation.io.exception;

import java.io.IOException;

public class WrongInputDataException extends RuntimeException {
    public WrongInputDataException(String message, IOException e) {
        super(message, e);
    }

}
