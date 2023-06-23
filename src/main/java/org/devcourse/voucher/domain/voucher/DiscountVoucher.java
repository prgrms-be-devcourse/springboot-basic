package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.amount.VoucherAmount;

public abstract class DiscountVoucher extends Voucher{

    protected DiscountVoucher(long id, VoucherType type, VoucherAmount amount) {
        super(id, type, amount);
    }

    @Override
    public Money retrievePostBalance(Money targetMoney) {
        int remainMoneyAmount = discount(targetMoney);

        return postProcessMoney(remainMoneyAmount);
    }

    private Money postProcessMoney(int amount) {
        if (amount < 0) {
            return Money.ZERO_AMOUNT;
        }
        return Money.of(amount);
    }

    protected abstract int discount(Money originPrice);
}
