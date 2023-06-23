package com.programmers.domain.voucher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return discountPercent.discount(beforeDiscount);
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
