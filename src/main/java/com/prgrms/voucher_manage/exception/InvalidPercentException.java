package com.prgrms.voucher_manage.exception;

public class InvalidPercentException extends RuntimeException{
    public InvalidPercentException() {
        super("Invalid Percent Range");
    }
}
