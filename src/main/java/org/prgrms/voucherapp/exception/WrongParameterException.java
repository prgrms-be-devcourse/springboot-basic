package org.prgrms.voucherapp.exception;

public class WrongParameterException extends RuntimeException{
    public WrongParameterException(String message){
        super(message);
    }
}
