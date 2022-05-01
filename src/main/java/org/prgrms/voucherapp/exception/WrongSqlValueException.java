package org.prgrms.voucherapp.exception;

public class WrongSqlValueException extends RuntimeException {
    public WrongSqlValueException(String message){
        super(message);
    }
}
