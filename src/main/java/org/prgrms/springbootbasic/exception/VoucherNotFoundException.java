package org.prgrms.springbootbasic.exception;


public class VoucherNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "해당하는 voucher가 존재하지 않습니다.";
    }
}
