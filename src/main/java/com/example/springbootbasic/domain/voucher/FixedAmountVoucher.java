package com.example.springbootbasic.domain.voucher;

import static com.example.springbootbasic.domain.voucher.VoucherEnum.*;

public class FixedAmountVoucher implements Voucher {
    private final Long voucherId;
    private final long amount;

    public FixedAmountVoucher(Long voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public VoucherEnum getVoucherEnum() {
        return FIXED_AMOUNT_VOUCHER;
    }

    @Override
    public long getDiscountValue() {
        return amount;
    }
}
