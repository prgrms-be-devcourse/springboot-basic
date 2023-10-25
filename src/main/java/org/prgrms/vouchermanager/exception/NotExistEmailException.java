package org.prgrms.vouchermanager.exception;

public class NotExistEmailException extends RuntimeException{
    public NotExistEmailException() {
        super("존재하지 않는 이메일입니다. 다시 입력해주세요");
    }

    public NotExistEmailException(String message) {
        super(message);
    }
}
