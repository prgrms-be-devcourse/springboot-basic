package org.prgrms.springbasic.utils.exception;

public class NonExistentCommand extends RuntimeException {

    public NonExistentCommand(String message) {
        super(message);
    }
}
