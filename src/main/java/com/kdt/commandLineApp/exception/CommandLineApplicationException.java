package com.kdt.commandLineApp.exception;

public class CommandLineApplicationException extends Exception {
    protected String msg;

    public CommandLineApplicationException() {}

    public CommandLineApplicationException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
