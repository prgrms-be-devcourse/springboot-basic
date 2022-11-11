package com.example.springbootbasic.domain.voucher;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;

public class FixedAmountVoucher extends Voucher {

    public FixedAmountVoucher(Long voucherId, Long discountValue) {
        super(voucherId, discountValue);
    }

    @Override
    public VoucherType getVoucherType() {
        return FIXED_AMOUNT;
    }
}
