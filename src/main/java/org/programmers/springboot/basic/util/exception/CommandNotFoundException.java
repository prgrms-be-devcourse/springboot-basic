package org.programmers.springboot.basic.util.exception;

public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException() {
        super("Exception Occurred: No matching commands found!");
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
