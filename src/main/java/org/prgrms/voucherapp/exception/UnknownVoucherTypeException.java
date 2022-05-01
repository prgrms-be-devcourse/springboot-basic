package org.prgrms.voucherapp.exception;

public class UnknownVoucherTypeException extends RuntimeException{
    public UnknownVoucherTypeException(String message){
        super(message);
    }
}
