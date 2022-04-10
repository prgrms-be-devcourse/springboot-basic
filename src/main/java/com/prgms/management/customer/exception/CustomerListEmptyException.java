package com.prgms.management.customer.exception;

public class CustomerListEmptyException extends CustomerException{
    private static final long serialVersionUID = 1259441181706814874L;

    public CustomerListEmptyException() {
        super("목록에 저장된 데이터가 없습니다.");
    }
}
