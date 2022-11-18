package org.prgrms.vouchermanagement.exception.customer;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
        super("존재하지 않는 고객입니다.");
    }
}
