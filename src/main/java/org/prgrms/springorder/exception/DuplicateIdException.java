package org.prgrms.springorder.exception;

public class DuplicateIdException extends RuntimeException {

    public DuplicateIdException(String message) {
        super(message);
    }

}
