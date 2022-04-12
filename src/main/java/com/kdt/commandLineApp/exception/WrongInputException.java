package com.kdt.commandLineApp.exception;

public class WrongInputException extends CommandLineApplicationException {
    public WrongInputException() {
        this.msg = "insert correct command";
    }
}
