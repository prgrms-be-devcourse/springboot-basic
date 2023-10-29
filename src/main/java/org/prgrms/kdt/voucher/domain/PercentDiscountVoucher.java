package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final int percent;
    private UUID customerId = null;

    public PercentDiscountVoucher(UUID voucherId, int percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    public PercentDiscountVoucher(UUID voucherId, int percent, UUID customerId) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.customerId = customerId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }

    @Override
    public long getAmount() {
        return 0;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public int getPercent() {
        return percent;
    }

    @Override
    public UUID getCustomerId() {
        return null;
    }

    @Override
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return String.format("PercentDiscountVoucher'{voucherId = %-40s, percent = %s}'", voucherId, percent);
    }
}
