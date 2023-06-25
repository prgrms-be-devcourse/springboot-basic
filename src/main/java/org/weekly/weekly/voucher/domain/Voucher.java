package org.weekly.weekly.voucher.domain;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;

    private final LocalDate registrationDate;
    private final LocalDate expirationDate;

    private long amount;

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

    public long applyDiscount(long beforeAmount) {
        long afterAmount = discount.applyDiscount(beforeAmount, amount);

        if (afterAmount >= 0) {
            return afterAmount;
        }

        this.amount -= afterAmount;
        return 0L;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[ID: {0}, 금액: {1}, 등록일자: {2}, 유효기간: {3}", voucherId,amount, registrationDate, expirationDate);
    }
}
