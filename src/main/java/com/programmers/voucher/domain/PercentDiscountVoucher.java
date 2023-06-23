package com.programmers.voucher.domain;

import com.programmers.exception.AmountValueException;

import java.time.LocalDate;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final DiscountPercent discountPercent;
    private final LocalDate createdDate;
    private final LocalDate expirationDate;

    public PercentDiscountVoucher(UUID voucherId, long percent, LocalDate localDate) {
        this.voucherId = voucherId;
        this.discountPercent = new DiscountPercent(percent);
        this.createdDate = localDate;
        this.expirationDate = expirationDate();
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        if (available()) {
            return discountPercent.discount(beforeDiscount);
        }
        throw new AmountValueException();
    }

    @Override
    public boolean available() {
        if (LocalDate.now().isAfter(expirationDate)) {
            return false;
        }
        return true;
    }

    public LocalDate expirationDate() {
        return createdDate.plusDays(7);
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher {" +
                "voucherId = " + voucherId +
                ", discountPercent = " + discountPercent + "%" +
                ", expirationDate = " + expirationDate +
                "}\n";
    }
}
