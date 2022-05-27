package org.prgrms.kdt.error;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException() {
        super("사용자를 찾을 수 없습니다.");
    }
}
