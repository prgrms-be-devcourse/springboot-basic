package com.programmers.domain.voucher;

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
        if (beforeDiscount > discountAmount.getAmount()) {
            return discountAmount.discount(beforeDiscount);
        }
        return 0;
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
