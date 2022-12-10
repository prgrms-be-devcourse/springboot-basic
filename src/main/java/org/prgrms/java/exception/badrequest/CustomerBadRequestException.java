package org.prgrms.java.exception.badrequest;

public class CustomerBadRequestException extends BadRequestException {
    public CustomerBadRequestException() {
        super("사용자에 대한 요청이 올바르지 않습니다.");
    }

    public CustomerBadRequestException(String message) {
        super(message);
    }
}
