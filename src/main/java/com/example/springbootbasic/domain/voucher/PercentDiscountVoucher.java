package com.example.springbootbasic.domain.voucher;

public class PercentDiscountVoucher implements Voucher {

    private final Long voucherId;
    private final long percent;

    public PercentDiscountVoucher(Long voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public Long getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long discountBefore) {
        return discountBefore * (this.percent / 100);
    }
}
