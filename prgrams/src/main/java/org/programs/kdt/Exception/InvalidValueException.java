package org.programs.kdt.Exception;

public class InvalidValueException extends BusinessException{
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
