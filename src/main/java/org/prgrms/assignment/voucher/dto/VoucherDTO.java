package org.prgrms.assignment.voucher.dto;

import org.prgrms.assignment.voucher.model.VoucherType;

import java.time.LocalDateTime;

public class VoucherDTO {
    private final VoucherType voucherType;
    private final long benefit;
    private final LocalDateTime createdAt;

    public VoucherDTO(VoucherType voucherType, long benefit, LocalDateTime createdAt) {
        this.voucherType = voucherType;
        this.benefit = benefit;
        this.createdAt = createdAt;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getBenefit() {
        return benefit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
