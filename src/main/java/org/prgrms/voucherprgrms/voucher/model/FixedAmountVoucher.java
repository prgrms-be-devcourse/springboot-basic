package org.prgrms.voucherprgrms.voucher.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;

    private static final String DTYPE = "FixedAmountVoucher";

    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return amount;
    }

    public String getDTYPE() {
        return DTYPE;
    }


    @Override
    public String toString() {
        return VoucherType.FIXEDAMOUNT.getName();
    }
}
