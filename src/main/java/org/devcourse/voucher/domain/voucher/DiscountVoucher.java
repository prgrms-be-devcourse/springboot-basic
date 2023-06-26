package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.amount.VoucherAmount;

public class DiscountVoucher extends Voucher{

    private final int amount;
    private final DiscountPolicy discountPolicy;

    protected DiscountVoucher(long id, VoucherType type, int amount, DiscountPolicy discountPolicy) {
        super(id, type);
        validateAmount(type, amount);
        this.amount = amount;
        this.discountPolicy = discountPolicy;
    }

    private void validateAmount(VoucherType type, int amount) {
        boolean incorrectAmountRange = switch (type) {
            case PERCENT -> amount < 0 || amount > 100_000;
            case FIX -> amount < 0 || amount > 100;
        };

        if (incorrectAmountRange) {
            throw new RuntimeException("바우처 금액 범위 오류");
        }
    }

    @Override
    public Money retrievePostBalance(Money targetMoney) {
        return discountPolicy.discount(targetMoney);
    }

}
