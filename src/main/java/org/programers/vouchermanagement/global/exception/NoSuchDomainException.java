package org.programers.vouchermanagement.global.exception;

public class NoSuchDomainException extends RuntimeException {

    public NoSuchDomainException(String message) {
        super(message);
    }

    public NoSuchDomainException() {
        this("존재하지 않는 도메인입니다.");
    }
}
