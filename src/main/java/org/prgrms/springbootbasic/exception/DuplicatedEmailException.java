package org.prgrms.springbootbasic.exception;


public class DuplicatedEmailException extends RuntimeException {
    @Override
    public String getMessage() {
        return "중복된 이메일입니다.";
    }
}
