package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    private final String name;
    private final Long minimumPriceCondition;
    private final LocalDateTime expirationDate;
    private VoucherState state = VoucherState.AVAILABLE;

    protected Voucher(UUID voucherId, String name, LocalDateTime expirationDate) {
        if (isInvalidVoucherId(voucherId)) {
            throw new IllegalArgumentException("잘못된 아이디 값");
        }
        this.voucherId = voucherId;
        if (isInvalidName(name)) {
            throw new IllegalArgumentException("잘못된 이름");
        }
        this.name = name;
        this.minimumPriceCondition = 0L;
        if (isInvalidExpirationDate(expirationDate)) {
            throw new IllegalArgumentException("잘못된 유효기간");
        }
        this.expirationDate = expirationDate;
    }

    protected Voucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime expirationDate) {
        if (isInvalidVoucherId(voucherId)) {
            throw new IllegalArgumentException("잘못된 아이디 값");
        }
        this.voucherId = voucherId;
        if (isInvalidName(name)) {
            throw new IllegalArgumentException("잘못된 이름");
        }
        this.name = name;
        this.minimumPriceCondition = minimumPriceCondition;
        if (isInvalidExpirationDate(expirationDate)) {
            throw new IllegalArgumentException("잘못된 유효기간");
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

    public VoucherState getState() {
        return state;
    }

    private boolean isInvalidVoucherId(UUID voucherId) {
        return voucherId == null;
    }

    private boolean isInvalidName(String name) {
        return name == null || name.isEmpty();
    }

    private boolean isInvalidExpirationDate(LocalDateTime expirationDate) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expirationDate);
    }


    protected boolean isExpiration(LocalDateTime now) {
        return now.isAfter(expirationDate);
    }

    protected void setVoucherState(VoucherState toChange) {
        state = toChange;
    }

    protected abstract Long getDiscountPrice(Long priceBeforeDiscount);

    protected abstract Long discount(Long priceBeforeDiscount);
}
