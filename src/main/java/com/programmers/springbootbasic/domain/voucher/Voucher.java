package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    private final String name;
    private final Long minimumPriceCondition;
    private final LocalDateTime expirationDate;
    private boolean used = false;

    protected Voucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime expirationDate) {
        if (isInvalidVoucherId(voucherId)) {
            throw new IllegalArgumentException("잘못된 아이디 값, voucherId=" + voucherId);
        }
        this.voucherId = voucherId;
        if (isInvalidName(name)) {
            throw new IllegalArgumentException("잘못된 이름, name=" + name);
        }
        this.name = name;
        this.minimumPriceCondition = minimumPriceCondition;
        if (isInvalidExpirationDate(expirationDate)) {
            throw new IllegalArgumentException("잘못된 유효기간, expirationDate=" + expirationDate);
        }
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public Long getMinimumPriceCondition() {
        return minimumPriceCondition;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public Long discount(Long priceBeforeDiscount) {
        used = true;
        return getDiscountPrice(priceBeforeDiscount);
    }

    public boolean isExpiration(LocalDateTime now) {
        return now.isAfter(expirationDate);
    }

    public boolean isUsed() {
        return used;
    }

    private boolean isInvalidVoucherId(UUID voucherId) {
        return voucherId == null;
    }

    private boolean isInvalidName(String name) {
        return name == null || name.isBlank();
    }

    private boolean isInvalidExpirationDate(LocalDateTime expirationDate) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expirationDate);
    }

    protected abstract Long getDiscountPrice(Long priceBeforeDiscount);
}
