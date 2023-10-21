package org.prgrms.kdtspringdemo.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;
    private final String voucherType;

    public FixedAmountVoucher(UUID voucherId, long amount, String voucherType) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public Long getAmount() {
        return this.amount;
    }

    @Override
    public String getVoucherType() {
        return this.voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        long totalCount = beforeDiscount - amount;
        if(totalCount < 0) {
            throw new RuntimeException("할인 가격보다 비싼 가격에만 적용 가능합니다.");
        }
        return totalCount;
    }
}
