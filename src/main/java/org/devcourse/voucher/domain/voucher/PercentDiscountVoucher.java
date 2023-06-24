package org.devcourse.voucher.domain.voucher;

import org.devcourse.voucher.domain.voucher.amount.VoucherAmount;

public class PercentDiscountVoucher extends DiscountVoucher{

    protected PercentDiscountVoucher(long id, VoucherType type, int percent) {
        super(id, type, VoucherAmount.of(type, percent));
    }

    @Override
    protected Money discount(Money originPrice) {
        int percentAmount = amount.getAmount();

        return originPrice.minus(originPrice.percent(percentAmount));
    }
}
