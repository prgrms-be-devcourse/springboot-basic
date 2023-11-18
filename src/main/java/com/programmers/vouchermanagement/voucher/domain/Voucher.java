package com.programmers.vouchermanagement.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Voucher {

    private final UUID voucherId;
    private final VoucherType voucherType;
    private final VoucherPolicy voucherPolicy;
    private final LocalDateTime createdAt;

    public Voucher(UUID voucherId, VoucherType voucherType, VoucherPolicy voucherPolicy, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.voucherPolicy = voucherPolicy;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
