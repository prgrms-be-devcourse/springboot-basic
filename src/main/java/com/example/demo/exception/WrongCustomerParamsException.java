package com.example.demo.exception;

public class WrongCustomerParamsException extends Exception {
    String msg;

    public WrongCustomerParamsException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
