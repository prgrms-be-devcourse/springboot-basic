package org.prgrms.kdtspringdemo.voucher.domain;

public class FixedDiscountPolicy implements VoucherPolicy {
    private final long amount;

    public FixedDiscountPolicy(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return this.amount;
    }

    @Override
    public String getVoucherType() {
        return "fixeddiscount";
    }

    @Override
    public long discount(long beforeDiscount) {
        long totalCount = beforeDiscount - amount;
        if(totalCount < 0) {
            throw new RuntimeException("할인 가격보다 비싼 가격에만 적용 가능합니다.");
        }
        return totalCount;
    }
}
