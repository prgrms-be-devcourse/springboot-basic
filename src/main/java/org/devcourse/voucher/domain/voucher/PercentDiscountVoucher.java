package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.amount.VoucherAmount;

public class PercentDiscountVoucher extends DiscountVoucher{

    protected PercentDiscountVoucher(long id, VoucherType type, int percent) {
        super(id, type, VoucherAmount.of(type, percent));
    }

    @Override
    protected int discount(Money originPrice) {
        int percent = amount.getAmount();

        return originPrice.amount() * (100 - percent) / 100;
    }

}
