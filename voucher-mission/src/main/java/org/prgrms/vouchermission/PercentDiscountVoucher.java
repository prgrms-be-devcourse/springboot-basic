package org.prgrms.vouchermission;

import java.time.LocalDate;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDate createdDate, LocalDate expirationDate) {
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdDate = createdDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discountAmount(long beforeDiscount) {
        return (beforeDiscount * percent) / 100;
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
