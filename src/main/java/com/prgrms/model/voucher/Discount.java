package com.prgrms.model.voucher;

public class Discount {
    private final long discount;

    public Discount(long discount) {
        validDiscount(discount < 0, "할인에 대한 인자값은 양수여야 합니다.");
        this.discount = discount;
    }

    private void validDiscount (boolean expression, String exceptionMessage){
        if(expression) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    public long getDiscount() {
        return discount;
    }

}
