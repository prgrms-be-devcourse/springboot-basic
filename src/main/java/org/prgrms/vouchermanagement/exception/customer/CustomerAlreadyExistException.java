package org.prgrms.vouchermanagement.exception.customer;

public class CustomerAlreadyExistException extends RuntimeException {
    public CustomerAlreadyExistException() {
        super("이미 존재하는 회원입니다. (이메일을 변경해주세요.)");
    }
}
