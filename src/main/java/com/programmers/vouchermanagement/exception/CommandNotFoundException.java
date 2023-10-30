package com.programmers.vouchermanagement.exception;

public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException(String wrongCommand) {
        super("The corresponding command does not exist. Input : " + wrongCommand);
    }
}
