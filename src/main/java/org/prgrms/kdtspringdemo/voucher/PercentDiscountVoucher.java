package org.prgrms.kdtspringdemo.voucher;

import java.text.MessageFormat;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private final UUID voucherId;
    private final long percent;

    @Override
    public String toString() {
        return MessageFormat.format("[PercentDiscountVoucher] voucherId : {0}, percent : {1}", voucherId, percent);
    }

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return percent;
    }

    @Override
    public String saveCSV() {
        return String.format("PercentDiscountVoucher,%s,%s", voucherId, percent);
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}
