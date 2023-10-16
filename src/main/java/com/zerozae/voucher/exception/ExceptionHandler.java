package com.zerozae.voucher.exception;

public class ExceptionHandler extends RuntimeException{
    public ExceptionHandler(String message) {
        super(message);
    }
    public static ExceptionHandler err(String message){
        return new ExceptionHandler(message);
    }
}
