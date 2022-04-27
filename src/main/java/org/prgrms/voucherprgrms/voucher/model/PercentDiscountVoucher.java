package org.prgrms.voucherprgrms.voucher.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private final String DTYPE = "PercentDiscountVoucher";

    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt;
    }

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public long getValue() {
        return percent;
    }

    public String getDTYPE() {
        return DTYPE;
    }

    @Override
    public String toString() {
        return VoucherType.PERCENTDISCOUNT.getName();
    }
}
