package org.prgrms.springbootbasic.exception;

public class NotValidVoucherTypeException extends RuntimeException {
    @Override
    public String getMessage() {
        return "잘못된 voucherType 입니다";
    }
}
