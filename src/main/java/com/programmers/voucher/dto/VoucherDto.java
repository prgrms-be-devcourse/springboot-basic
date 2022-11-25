package com.programmers.voucher.dto;

import com.programmers.voucher.voucher.VoucherType;

import java.util.UUID;

public class VoucherDto {
    private UUID voucherId;
    private VoucherType type;
    private long value;
    private boolean isAssigned;

    public VoucherDto(UUID voucherId, VoucherType type, long value, boolean isAssigned) {
        this.voucherId = voucherId;
        this.type = type;
        this.value = value;
        this.isAssigned = isAssigned;
    }

    public VoucherType getType() {
        return type;
    }

    public void setType(VoucherType type) {
        this.type = type;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(UUID voucherId) {
        this.voucherId = voucherId;
    }
}
