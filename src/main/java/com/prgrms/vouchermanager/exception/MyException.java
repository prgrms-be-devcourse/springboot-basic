package com.prgrms.vouchermanager.exception;

public abstract class MyException extends RuntimeException {

    public MyException(String message) {
        super(message);
    }

    public String consoleMessage() {
        return "";
    }
}
