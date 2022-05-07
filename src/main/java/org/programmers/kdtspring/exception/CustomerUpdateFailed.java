package org.programmers.kdtspring.exception;

public class CustomerUpdateFailed extends RuntimeException {
    public CustomerUpdateFailed(String message) {
        super(message);
    }
}