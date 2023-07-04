package org.programers.vouchermanagement.global.exception;

public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException() {
        this("존재하지 않는 엔티티입니다.");
    }
}
