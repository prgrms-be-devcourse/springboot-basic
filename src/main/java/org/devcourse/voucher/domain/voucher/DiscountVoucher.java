package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.amount.VoucherAmount;

public class DiscountVoucher extends Voucher{

    private final VoucherAmount amount;
    private final DiscountPolicy discountPolicy;

    protected DiscountVoucher(long id, VoucherType type, int amount, DiscountPolicy discountPolicy) {
        super(id, type);
        this.amount = VoucherAmount.of(type, amount);
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Money retrievePostBalance(Money targetMoney) {
        return discountPolicy.discount(targetMoney);
    }

}
