package com.example.springbootbasic.domain.voucher;

public class FixedAmountVoucher implements Voucher {

    private final Long voucherId;
    private final long amount;

    public FixedAmountVoucher(Long voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public Long getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long discountBefore) {
        return discountBefore - this.amount;
    }
}
