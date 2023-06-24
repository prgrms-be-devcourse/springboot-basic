package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.amount.VoucherAmount;

public abstract class DiscountVoucher extends Voucher{

    protected DiscountVoucher(long id, VoucherType type, VoucherAmount amount) {
        super(id, type, amount);
    }

    @Override
    public Money retrievePostBalance(Money targetMoney) {
        return discount(targetMoney);
    }

    protected abstract Money discount(Money originPrice);
}
