package org.prgrms.assignment.voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;


public class Voucher {

    // mapping용 프로퍼티.
    private UUID voucherId;
    private VoucherType voucherType;
    private LocalDateTime createdAt;
    private long benefit;

    public Voucher() {
    }

    public Voucher(UUID voucherId, VoucherType voucherType, long benefit, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.benefit = benefit;
        this.createdAt = createdAt;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getBenefit() {
        return benefit;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setBenefit(long benefit) {
        this.benefit = benefit;
    }
}
