package org.prgrms.kdt.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.prgrms.kdt.voucher.domain.VoucherType.FIXED;

public class FixedAmountVoucher extends Voucher {

    private final String type = FIXED.getType();

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt, UUID customerId) {
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.customerId = customerId;
    }

    public static FixedAmountVoucher fromDto(VoucherDto voucherDto) {
        if (voucherDto == null) {
            throw new IllegalArgumentException("Voucher DTO cannot be null");
        }

        if (voucherDto.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount should be greater than 0");
        }

        if (voucherDto.getAmount() >= 100_000) {
            throw new IllegalArgumentException("Amount should be less than 100,000");
        }

        if (!FIXED.getType().equals(voucherDto.getType())) {
            throw new IllegalArgumentException("Invalid voucher type for FixedAmountVoucher");
        }

        return new FixedAmountVoucher(voucherDto.getVoucherId(), voucherDto.getAmount(), voucherDto.getCreatedAt());
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "type='" + type + '\'' +
                ", voucherId=" + voucherId +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                ", customerId=" + customerId +
                '}';
    }
}
