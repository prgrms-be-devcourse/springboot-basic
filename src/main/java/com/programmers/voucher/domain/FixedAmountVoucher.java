package com.programmers.voucher.domain;

import com.programmers.exception.AmountValueException;
import com.programmers.exception.VoucherExpirationException;

import java.time.LocalDate;
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
        if (available()) {
            return discountAmount.discount(beforeDiscount);
        }
        throw new AmountValueException();
    }

    @Override
    public boolean available() {
        if (LocalDate.now().isAfter(expirationDate)) {
            throw new VoucherExpirationException();
        }
        return true;
    }

    public LocalDate expirationDate() {
        return createdDate.plusDays(7);
    }

}
