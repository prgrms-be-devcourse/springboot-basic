package org.prgrms.vouchermission;

import java.time.LocalDate;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDate createdDate, LocalDate expirationDate) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discountAmount(long beforeDiscount) {
        return amount;
    }

    @Override
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
