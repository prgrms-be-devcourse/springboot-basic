package com.dojinyou.devcourse.voucherapplication.voucher.domain;


import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherCreateRequest;

import java.time.LocalDateTime;

public abstract class Voucher {
    private final Long id;
    private final VoucherType type;
    private final VoucherAmount amount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    protected Voucher(Long id, VoucherType type, VoucherAmount amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Voucher of(VoucherType type, VoucherAmount amount) {
        return of(null, type, amount, null, null);
    }
    public static Voucher of(Long id, VoucherType type, VoucherAmount amount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        switch (type) {
            case FIXED:
                return new FixedAmountVoucher(id, type, (FixedAmount) amount, createdAt, updatedAt);
            case PERCENT:
                return new PercentAmountVoucher(id, type, (PercentAmount) amount, createdAt, updatedAt);
        }
        throw new IllegalArgumentException();
    }

    public static Voucher from(VoucherCreateRequest createRequest) {
        return of(createRequest.getType(), createRequest.getAmount());
    }


    public Long getId() {
        return id;
    }

    public VoucherType getType() {
        return type;
    }

    public VoucherAmount getAmount() {
        return amount;
    }

    public int getAmountValue() {
        return amount.getValue();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public abstract double discount(double originAmount);
}
