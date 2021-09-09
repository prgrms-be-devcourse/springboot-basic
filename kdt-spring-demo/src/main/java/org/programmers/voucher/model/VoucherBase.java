package org.programmers.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherBase implements Voucher{
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long voucherValue;
    private final LocalDateTime createdAt;

    public VoucherBase(UUID voucherId, VoucherType voucherType, long voucherValue, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.voucherValue = voucherValue;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long getVoucherValue() {
        return voucherValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Voucher ID : " + voucherId + " / " + "VoucherType : " + voucherType + " / " + "Amount or Percent : " + voucherValue;
    }
}
