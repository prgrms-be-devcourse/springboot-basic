package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.amount.VoucherAmount;

public class FixedAmountVoucher extends DiscountVoucher{

    protected FixedAmountVoucher(long id, VoucherType type, int amount) {
        super(id, type, VoucherAmount.of(type, amount));
    }

    @Override
    protected int discount(Money originPrice) {
        return originPrice.amount() - amount.getAmount();
    }

}
