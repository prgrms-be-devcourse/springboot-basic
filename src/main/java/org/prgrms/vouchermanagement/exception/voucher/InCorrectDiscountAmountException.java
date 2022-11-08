package org.prgrms.vouchermanagement.exception.voucher;

public class InCorrectDiscountAmountException extends RuntimeException{
    public InCorrectDiscountAmountException() {
        super("정상 범위의 할인값을 입력해주세요.");
    }
}
