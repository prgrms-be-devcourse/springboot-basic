package com.programmers.voucher.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private final LocalDateTime createdDate = LocalDateTime.now();
    private final Integer expirationDate;

    public PercentDiscountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.percent = amount;
        this.expirationDate = expirationDate();
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
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
