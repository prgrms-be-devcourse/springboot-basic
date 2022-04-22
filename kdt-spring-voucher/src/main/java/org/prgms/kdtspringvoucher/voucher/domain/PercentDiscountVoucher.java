package org.prgms.kdtspringvoucher.voucher.domain;

import org.prgms.kdtspringvoucher.customer.domain.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {
    private static final long MAX_VOUCHER_PERCENT = 100;
    private static final long ZERO = 0;

    private final UUID voucherId;
    private UUID customerId;
    private Long percent;
    private VoucherType voucherType;
    private final LocalDateTime createdAt;

    public PercentDiscountVoucher(UUID voucherId, Long percent, VoucherType voucherType, LocalDateTime createdAt) {
        if (percent <= ZERO)
            throw new IllegalArgumentException("Percent should be over zero");
        if (percent > MAX_VOUCHER_PERCENT)
            throw new IllegalArgumentException("Percent should not be over %d".formatted(MAX_VOUCHER_PERCENT));

        this.voucherId = voucherId;
        this.percent = percent;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, Long percent, VoucherType voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.percent = percent;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public Long getAmount() {
        return percent;
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
    public void changeAmount(Long amount) {
        this.percent = amount;
    }

    @Override
    public void assignVoucherToCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
    }


    @Override
    public String toString() {
        return "voucherId=" + voucherId +
                ", customerId=" + customerId +
                ", percent=" + percent +
                ", voucherType=" + voucherType +
                ", createdAt=" + createdAt;
    }
}
