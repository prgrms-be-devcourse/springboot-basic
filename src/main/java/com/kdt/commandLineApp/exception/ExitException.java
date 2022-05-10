package com.kdt.commandLineApp.exception;

public class ExitException extends CommandLineApplicationException {
    public ExitException() {
        this.msg = "program successfully exit";
    }

    public ExitException(String msg) {
        super(msg);
    }
}
