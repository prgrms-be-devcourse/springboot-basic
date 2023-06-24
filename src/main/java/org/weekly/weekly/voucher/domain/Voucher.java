package org.weekly.weekly.voucher.domain;

import java.time.LocalDate;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final long amount;

    private final LocalDate registrationDate;
    private final LocalDate expirationDate;

    private Discount discount;

    public Voucher(UUID voucherId, long amount, LocalDate registrationDate, LocalDate expirationDate, Discount discount) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
        this.discount = discount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
