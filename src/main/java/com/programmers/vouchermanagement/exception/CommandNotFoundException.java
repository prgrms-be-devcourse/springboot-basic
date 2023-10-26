package com.programmers.vouchermanagement.exception;

public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException() {
        super("The corresponding command does not exist. ");
    }
}
