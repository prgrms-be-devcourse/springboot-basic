package org.prgrms.kdt.exception;

public class NotDevelopException extends RuntimeException {
    public NotDevelopException() {
        super("해당 기능은 현재 개발 구현 중 입니다.");
    }
}
