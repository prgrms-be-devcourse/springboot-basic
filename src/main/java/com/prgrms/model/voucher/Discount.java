package com.prgrms.model.voucher;

public class Discount {
    private final String negativeException = "할인에 대한 인자값은 양수여야 합니다.";
    private final long discount;

    public Discount(long discount) {
        validDiscount(discount < 0);
        this.discount = discount;
    }

    private void validDiscount(boolean expression) {
        if (expression) {
            throw new IllegalArgumentException(negativeException);
        }
    }

    public long getDiscount() {
        return discount;
    }

}
