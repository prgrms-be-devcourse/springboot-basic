package com.example.springbootbasic.domain.voucher;

import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;

public class PercentDiscountVoucher extends Voucher {

    public PercentDiscountVoucher(Long voucherId, Long discountValue) {
        super(voucherId, discountValue);
    }

    @Override
    public VoucherType getVoucherType() {
        return PERCENT_DISCOUNT;
    }
}
