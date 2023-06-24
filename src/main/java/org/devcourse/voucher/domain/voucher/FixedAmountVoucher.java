package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.amount.VoucherAmount;

public class FixedAmountVoucher extends DiscountVoucher{

    protected FixedAmountVoucher(long id, VoucherType type, int amount) {
        super(id, type, VoucherAmount.of(type, amount));
    }

    @Override
    protected Money discount(Money originPrice) {
        Money discountAmount = Money.of(amount.getAmount());

        return originPrice.minus(discountAmount);
    }

}
