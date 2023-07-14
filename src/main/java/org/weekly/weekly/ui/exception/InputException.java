package org.weekly.weekly.ui.exception;

import org.weekly.weekly.util.ExceptionCode;

public class InputException extends RuntimeException {

    public InputException(ExceptionCode exception) {
        super(exception.getMessage());
    }
}
