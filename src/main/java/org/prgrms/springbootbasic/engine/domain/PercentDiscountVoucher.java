package org.prgrms.springbootbasic.engine.domain;

import org.prgrms.springbootbasic.engine.enumtype.ErrorCode;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;
import org.prgrms.springbootbasic.exception.VoucherValueRangeException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private Integer percent;
    private final VoucherType voucherType;
    private final LocalDateTime createdAt;
    private UUID customerId;

    public PercentDiscountVoucher(UUID voucherId, Integer percent, LocalDateTime createdAt) {
        validateValue(percent);
        this.voucherId = voucherId;
        this.percent = percent;
        this.createdAt = createdAt;
        this.voucherType = VoucherType.PERCENT_DISCOUNT;
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, Integer percent, LocalDateTime createdAt) {
        validateValue(percent);
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.percent = percent;
        this.createdAt = createdAt;
        this.voucherType = VoucherType.PERCENT_DISCOUNT;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public void changeValue(Integer value) {
        validateValue(value);
        this.percent = value;
    }

    @Override
    public void changeOwner(Customer customer) {
        this.customerId = customer.getCustomerId();
    }

    @Override
    public void changeOwnerById(UUID customerId) {
        this.customerId = customerId;
    }

    @Override
    public void revokeOwner() {
        this.customerId = null;
    }

    @Override
    public String toFileString() {
        return voucherId + "\nPercentDiscount\n" + percent + "\n" + createdAt;
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
    public Integer getValue() {
        return this.percent;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public Optional<UUID> getCustomerId() {
        return Optional.ofNullable(this.customerId);
    }

    private void validateValue(Integer percent) {
        if (percent < 0) {
            throw new VoucherValueRangeException("Percent discount should be positive.", ErrorCode.VALUE_RANGE_OUT);
        } else if (percent == 0) {
            throw new VoucherValueRangeException("Percent discount shouldn't be zero.", ErrorCode.VALUE_RANGE_OUT);
        } else if (percent > 100) {
            throw new VoucherValueRangeException("Percent discount should be less than 100.", ErrorCode.VALUE_RANGE_OUT);
        }
    }
}
