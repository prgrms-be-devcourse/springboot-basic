package org.prgrms.spring_week1.Voucher.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long discount;
    private VoucherStatus voucherStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final VoucherType voucherType;
    private final UUID customerId;


    public FixedAmountVoucher(UUID voucherId, long discount, UUID customerId) {
        if (discount <= 0) {
            throw new IllegalArgumentException("amount should be plus");
        } else if (discount > 10000000) {
            throw new IllegalArgumentException("amount should be smaller than 10,000,000");
        }
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherStatus = VoucherStatus.VALID;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.voucherType = VoucherType.FIXEDAMOUNT;
        this.customerId = customerId;
    }

    public FixedAmountVoucher(UUID voucherId, long discount,
        VoucherStatus voucherStatus, LocalDateTime createdAt, LocalDateTime updatedAt,
        VoucherType voucherType, UUID customerId) {
        this.voucherId = voucherId;
        this.discount = discount;
        this.voucherStatus = voucherStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.voucherType = voucherType;
        this.customerId = customerId;
    }

    public void setVoucherStatus(VoucherStatus voucherStatus) {
        this.voucherStatus = voucherStatus;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount > discount ? beforeDiscount - discount : 0;
    }


    @Override
    public UUID getVoucherId() {
        return voucherId;
    }


    public long getDiscount() {
        return discount;
    }

    @Override
    public VoucherStatus getVoucherStatus() {
        return voucherStatus;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
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
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public void setStatus(VoucherStatus status) {
        this.voucherStatus = status;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "discount=" + discount +
            ", voucherStatus=" + voucherStatus +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", voucherType=" + voucherType +
            ", customerId=" + customerId +
            '}';
    }
}
