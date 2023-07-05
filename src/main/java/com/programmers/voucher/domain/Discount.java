package com.programmers.voucher.domain;

public abstract class Discount {

    // 여기서의 VoucherType은 사실상 Discount의 구현체 타입과 동일하므로
    // 이를 DiscountType으로 변수명을 변경하면 어떨지 고민입니다.
    private final DiscountType discountType;

    protected Discount(DiscountType discountType) {
        this.discountType = discountType;
    }

    public static Discount of(DiscountType discountType, long value) {
        return switch (discountType) {
            case FIXED -> new FixedDiscount(value);
            case PERCENT -> new PercentDiscount(value);
        };
    }

    public abstract long applyDiscount(long beforeDiscount);

    public abstract long getAmount();

    public DiscountType getVoucherType() {
        return discountType;
    }
}
