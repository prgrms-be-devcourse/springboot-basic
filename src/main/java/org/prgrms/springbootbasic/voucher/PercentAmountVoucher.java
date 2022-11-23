package org.prgrms.springbootbasic.voucher;


import java.util.UUID;

public class PercentAmountVoucher implements Voucher {
    private final UUID voucherId = UUID.randomUUID();
    private final long percent;

    public PercentAmountVoucher(long percent) {
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - percent);
    }

    @Override
    public String toString() {
        return "{PercentAmountVoucher- id: %s, percent: %d}".formatted(voucherId, percent);
    }
}
