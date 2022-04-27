package org.prgrms.spring_week1.Voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discount;
    private VoucherStatus voucherStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final VoucherType voucherType;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        if (percent <= 0) {
            throw new IllegalArgumentException("percent should not be minus");
        } else if (percent > 100) {
            throw new IllegalArgumentException("percent should be smaller than 100");
        }
        this.voucherId = voucherId;
        this.discount = percent;
        this.voucherStatus = VoucherStatus.VALID;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.voucherType = VoucherType.PERCENTDISCOUNT;
    }

    public PercentDiscountVoucher(UUID voucherId, long discount,
        VoucherStatus voucherStatus, LocalDateTime createdAt, LocalDateTime updatedAt,
        VoucherType voucherType) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherStatus = voucherStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.voucherType = voucherType;
    }

    public void setVoucherStatus(VoucherStatus voucherStatus) {
        this.voucherStatus = voucherStatus;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - beforeDiscount * discount / 100;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherStatus getVoucherStatus() {
        return voucherStatus;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getDiscount() {
        return discount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setStatus(VoucherStatus status) {
        this.voucherStatus = status;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
            " discount=" + discount +
            "/ voucherStatus=" + voucherStatus +
            '}';
    }
}
