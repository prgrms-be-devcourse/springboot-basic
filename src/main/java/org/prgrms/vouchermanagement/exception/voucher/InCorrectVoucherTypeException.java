package org.prgrms.vouchermanagement.exception.voucher;

public class InCorrectVoucherTypeException extends RuntimeException {
    public InCorrectVoucherTypeException() {
        super("지원하지 않는 타입의 바우처입니다.");
    }
}
