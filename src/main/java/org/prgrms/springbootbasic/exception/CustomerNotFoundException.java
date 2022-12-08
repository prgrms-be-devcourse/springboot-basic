package org.prgrms.springbootbasic.exception;

public class CustomerNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "해당하는 customer가 존재하지 않습니다.";
    }
}
