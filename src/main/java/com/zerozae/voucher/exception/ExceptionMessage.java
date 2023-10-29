package com.zerozae.voucher.exception;

public class ExceptionMessage extends RuntimeException{

    public ExceptionMessage(String message) {
        super(message);
    }

    public static ExceptionMessage error(String message) {
        return new ExceptionMessage(message);
    }
}
