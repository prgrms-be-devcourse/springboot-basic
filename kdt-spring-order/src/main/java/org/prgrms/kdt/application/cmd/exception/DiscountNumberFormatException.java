package org.prgrms.kdt.application.cmd.exception;

public class DiscountNumberFormatException extends RuntimeException{
    public DiscountNumberFormatException(String errorMessage) {
        super(errorMessage);
    }
}
