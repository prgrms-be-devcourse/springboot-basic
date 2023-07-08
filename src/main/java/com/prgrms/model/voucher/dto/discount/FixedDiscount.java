package com.prgrms.model.voucher.dto.discount;

public class FixedDiscount extends Discount {

    private final int limit = 10_000;
    private final String limitException = "할인 금액은" + limit + "원을 넘을 수 없습니다.";

    public FixedDiscount(double value) {
        super(value);
    }

    @Override
    protected void validLimit(double value) {
        if (value > limit) {
            throw new IllegalArgumentException(limitException);
        }
    }
}
