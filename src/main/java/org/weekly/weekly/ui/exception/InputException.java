package org.weekly.weekly.ui.exception;

import org.weekly.weekly.util.ExceptionMsg;

public class InputException extends RuntimeException{
    public InputException(ExceptionMsg exception) {
        super(exception.getMsg());
    }
}
