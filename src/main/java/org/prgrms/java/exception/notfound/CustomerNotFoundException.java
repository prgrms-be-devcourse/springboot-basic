package org.prgrms.java.exception.notfound;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("해당 사용자를 찾을 수 없습니다.");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
