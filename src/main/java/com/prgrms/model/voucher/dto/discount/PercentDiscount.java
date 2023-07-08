package com.prgrms.model.voucher.dto.discount;

public class PercentDiscount extends Discount {

    private final int limit = 100;
    private final String limitException = "할인율은" + limit + "%를 넘을 수 없습니다.";

    public PercentDiscount(double value){
        super(value);
    }

    @Override
    protected void validLimit(double value) {
        if (value >= limit) {
            throw new IllegalArgumentException(limitException);
        }
    }
}
