package com.example.springbootbasic.domain.voucher;

import static com.example.springbootbasic.domain.voucher.VoucherEnum.*;

public class PercentDiscountVoucher implements Voucher {
    private final Long voucherId;
    private final long percent;

    public PercentDiscountVoucher(Long voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public VoucherEnum getVoucherEnum() {
        return PERCENT_DISCOUNT_VOUCHER;
    }

    @Override
    public long getDiscountValue() {
        return percent;
    }
}
