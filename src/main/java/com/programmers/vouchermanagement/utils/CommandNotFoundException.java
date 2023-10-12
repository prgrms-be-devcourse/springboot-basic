package com.programmers.vouchermanagement.utils;

public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException() {
        super("The corresponding command does not exist. ");
    }
}
