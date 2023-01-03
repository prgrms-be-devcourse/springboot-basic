package org.prgrms.kdt.presentation.io.exception;

import java.io.IOException;

public class WrongOutputDataException extends RuntimeException {
    public WrongOutputDataException(String message, IOException e) {
        super(message, e);
    }
}
