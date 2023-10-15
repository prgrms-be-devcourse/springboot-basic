package com.prgrms.voucher_manage.exception;

public class EmptyInputException extends RuntimeException {
    public EmptyInputException() {
        super("값을 입력해주세요.");
    }
}
