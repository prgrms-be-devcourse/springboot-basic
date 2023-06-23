package com.programmers.springbootbasic.domain.voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Voucher {
    private final UUID voucherId;
    private final String name;
    private final Long minimumPrice;
    private final LocalDateTime expirationDate;
    private VoucherState state = VoucherState.AVAILABLE;

    protected Voucher(UUID voucherId, String name, LocalDateTime expirationDate) {
        this.voucherId = voucherId;
        this.name = name;
        this.minimumPrice = 0L;
        this.expirationDate = expirationDate;
    }

    protected Voucher(UUID voucherId, String name, Long minimumPrice, LocalDateTime expirationDate) {
        this.voucherId = voucherId;
        this.name = name;
        this.minimumPrice = minimumPrice;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public Long getMinimumPrice() {
        return minimumPrice;
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
