package org.prgrms.vouchermanagement.exception.validation;

public class NotNumberException extends RuntimeException{
    public NotNumberException() {
        super("숫자를 입력해주세요.");
    }
}
