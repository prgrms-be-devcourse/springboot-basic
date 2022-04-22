package org.prgms.kdtspringvoucher.voucher.domain;

import org.prgms.kdtspringvoucher.customer.domain.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final long MAX_VOUCHER_AMOUNT = 100000;
    private static final long MIN_VOUCHER_AMOUNT = 0;

    private final UUID voucherId;
    private UUID customerId;
    private Long amount;
    private VoucherType voucherType;
    private final LocalDateTime createdAt;

    public FixedAmountVoucher(UUID voucherId, Long amount, VoucherType voucherType, LocalDateTime createdAt) {
        if (amount > MAX_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("Amount should be under %d".formatted(MAX_VOUCHER_AMOUNT));
        if (amount < MIN_VOUCHER_AMOUNT)
            throw new IllegalArgumentException("Amount should be over %d".formatted(MIN_VOUCHER_AMOUNT));

        this.voucherId = voucherId;
        this.amount = amount;
        this.voucherType = voucherType;
        this.createdAt = createdAt;
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, Long amount, VoucherType voucherType, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.amount = amount;
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
        return amount;
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
        this.amount = amount;
    }

    @Override
    public void assignVoucherToCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
    }


    @Override
    public String toString() {
        return "voucherId=" + voucherId +
                ", customerId=" + customerId +
                ", amount=" + amount +
                ", voucherType=" + voucherType +
                ", createdAt=" + createdAt;
    }
}
