package org.prgrms.springbasic.utils.exception;

public class NoDatabaseChange extends RuntimeException {

    public NoDatabaseChange(String message) {
        super(message);
    }
}
