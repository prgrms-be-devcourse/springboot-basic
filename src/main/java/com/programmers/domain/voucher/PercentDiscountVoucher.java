package com.programmers.domain.voucher;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final DiscountPercent discountPercent;
    private final LocalDateTime createdDate = LocalDateTime.now();
    private final Integer expirationDate;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.discountPercent = new DiscountPercent(amount);
        this.expirationDate = expirationDate();
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return discountPercent.discount(beforeDiscount);
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
