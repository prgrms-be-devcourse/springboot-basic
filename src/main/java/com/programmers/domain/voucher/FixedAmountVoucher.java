package com.programmers.domain.voucher;

import com.programmers.exception.AmountValueException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final DiscountAmount discountAmount;
    private final LocalDateTime createdDate = LocalDateTime.now();
    private final Integer expirationDate;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = new DiscountAmount(discountAmount);
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
        if (parsedDate() > expirationDate) {
            return false;
        }
        return true;
    }

    @Override
    public Integer expirationDate() {
        return Integer.parseInt(createdDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))) + 7;
    }

    @Override
    public Integer parsedDate() {
        return Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
}
