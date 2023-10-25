package org.prgrms.vouchermanager.exception;

public class NotExistEmailException extends RuntimeException{
    public NotExistEmailException() {
        super("해당 이메일을 가진 고객은 존재하지 않습니다. 다시 입력해주세요");
    }

    public NotExistEmailException(String message) {
        super(message);
    }
}
