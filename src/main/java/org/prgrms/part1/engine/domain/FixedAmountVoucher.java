package org.prgrms.part1.engine.domain;

import org.prgrms.part1.engine.enumtype.VoucherType;
import org.prgrms.part1.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 1000000;
    private final UUID voucherId;
    private long amount;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    private UUID customerId;

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        validateValue(amount);
        this.voucherId = voucherId;
        this.amount = amount;
        this.createdAt = createdAt;
        this.voucherType = VoucherType.FixedAmount;
    }

    @Override
    public void changeValue(long value) {
        validateValue(value);
        this.amount = value;
    }

    @Override
    public void changeOwner(Customer customer) {
        this.customerId = customer.getCustomerId();
    }

    @Override
    public void revokeOwner() {
        this.customerId = null;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public String toFileString() {
        return voucherId + "\nFixedAmount\n" + amount + "\n" + createdAt;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    @Override
    public Long getValue() {
        return amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public Optional<UUID> getCustomerId() {
        return Optional.ofNullable(this.customerId);
    }

    private void validateValue(Long amount) {
        if (amount < 0) {
            throw new VoucherException("Amount should be positive");
        } else if (amount == 0) {
            throw new VoucherException("Amount shouldn't be zero");
        } else if (amount > MAX_VOUCHER_AMOUNT) {
            throw new VoucherException("Amount should be less than 1000001");
        }
    }


}
