package com.prgrms.devkdtorder.exception;

public class NotFoundException extends BusinessException{

    public NotFoundException(ErrorType errorType) {
        super(errorType);
    }
}
