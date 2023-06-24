package org.weekly.weekly.voucher.domain;

import java.time.LocalDate;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;

    private final LocalDate registrationDate;
    private final LocalDate expirationDate;

    public PercentDiscountVoucher(UUID voucherId, long amount, LocalDate registrationDate, LocalDate expirationDate) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
