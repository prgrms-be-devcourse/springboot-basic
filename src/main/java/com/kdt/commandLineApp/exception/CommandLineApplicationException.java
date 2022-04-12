package com.kdt.commandLineApp.exception;

public class CommandLineApplicationException extends Exception {
    protected String msg;

    @Override
    public String getMessage() {
        return msg;
    }
}
