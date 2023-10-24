package com.zerozae.voucher.exception;

public class ErrorMessage extends RuntimeException{

    public ErrorMessage(String message) {
        super(message);
    }

    public static ErrorMessage error(String message) {
        return new ErrorMessage(message);
    }
}