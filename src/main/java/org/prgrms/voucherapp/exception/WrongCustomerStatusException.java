package org.prgrms.voucherapp.exception;

public class WrongCustomerStatusException extends RuntimeException{
    public WrongCustomerStatusException(String message){
        super(message);
    }
}
