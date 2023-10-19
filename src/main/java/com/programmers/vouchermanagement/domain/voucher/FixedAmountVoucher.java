package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;

@Getter
public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(long amount) {
        super(amount);
    }

    @Override
    public VoucherType getType() {
        return VoucherType.FIXED_AMOUNT;
    }
}
