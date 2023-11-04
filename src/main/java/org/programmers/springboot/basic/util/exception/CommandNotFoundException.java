package org.programmers.springboot.basic.util.exception;

public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException() {
        super("No matching commands found!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
