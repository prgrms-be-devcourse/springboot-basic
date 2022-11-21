package org.prgrms.kdt.presentation.io.exception;

import java.io.IOException;

public class WrongOutputDataException extends RuntimeException {
    public WrongOutputDataException(String messge, IOException e) {
        super(messge, e);
    }
}
