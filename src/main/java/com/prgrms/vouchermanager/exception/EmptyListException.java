package com.prgrms.vouchermanager.exception;

public class EmptyListException extends RuntimeException {
    public EmptyListException() {
        super("There is nothing registered.");
    }
}
