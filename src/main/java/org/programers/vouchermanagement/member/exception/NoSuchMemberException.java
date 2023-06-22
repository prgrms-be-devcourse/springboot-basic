package org.programers.vouchermanagement.member.exception;

public class NoSuchMemberException extends RuntimeException {

    public NoSuchMemberException(String message) {
        super(message);
    }

    public NoSuchMemberException() {
        this("존재하지 않는 회원입니다.");
    }
}
