package org.prgrms.springbootbasic.exception;

public class InValidVoucherDateException extends RuntimeException {
    @Override
    public String getMessage() {
        return "voucher 날짜가 유효하지 않습니다.";
    }
}
