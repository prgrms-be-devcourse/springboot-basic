package com.programmers.domain.voucher;

import com.programmers.exception.AmountValueException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final DiscountAmount discountAmount;
    private final LocalDate expirationDate;
    private final LocalDate createdDate;

    public FixedAmountVoucher(UUID voucherId, long discountAmount, LocalDate localDate) {
        this.voucherId = voucherId;
        this.discountAmount = new DiscountAmount(discountAmount);
        this.createdDate = localDate;
        this.expirationDate = expirationDate();
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        if (discountAmount.discountable(beforeDiscount)) {
            return discountAmount.discount(beforeDiscount);
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

}
