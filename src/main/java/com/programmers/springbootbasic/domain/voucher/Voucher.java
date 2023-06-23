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
        this.voucherId = voucherId;
        this.name = name;
        this.minimumPriceCondition = 0L;
        this.expirationDate = expirationDate;
    }

    protected Voucher(UUID voucherId, String name, Long minimumPriceCondition, LocalDateTime expirationDate) {
        this.voucherId = voucherId;
        this.name = name;
        this.minimumPriceCondition = minimumPriceCondition;
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

    public void use() {
        setVoucherState(VoucherState.USED);
    }

    protected boolean isExpiration(LocalDateTime now) {
        return now.isAfter(expirationDate);
    }

    protected void setVoucherState(VoucherState toChange) {
        state = toChange;
    }

    protected abstract Long discount(Long priceBeforeDiscount);
}
