package com.kdt.commandLineApp.exception;

public class WrongCommandException extends CommandLineApplicationException {
    public WrongCommandException() {
        this.msg = "Input right command";
    }

    public WrongCommandException(String msg) { super(msg); }
}
