package org.prgms.voucher.exception;

public class ZeroDiscountPercentException extends Exception {
    public ZeroDiscountPercentException() {
        super("[ERROR] 할인퍼센트가 0입니다.");
    }
}
