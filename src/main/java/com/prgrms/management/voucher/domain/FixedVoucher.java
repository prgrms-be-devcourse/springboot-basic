package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessageType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FixedVoucher extends Voucher {
    private static final long MAX_FIXED_DISCOUNT = 10000L;
    private static final long MIN_FIXED_DISCOUNT = 0L;
    private final long amount;
    private final VoucherType voucherType;

    public FixedVoucher(long amount, VoucherType voucherType, UUID customerId) {
        super(customerId);
        validateFixedAmount(amount);
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public FixedVoucher(UUID voucherId, LocalDateTime createdAt, UUID customerId, long amount, VoucherType voucherType) {
        super(voucherId, createdAt, customerId);
        validateFixedAmount(amount);
        this.amount = amount;
        this.voucherType = voucherType;
    }

    private void validateFixedAmount(long amount) {
        if (amount < MIN_FIXED_DISCOUNT || amount > MAX_FIXED_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + ErrorMessageType.OUT_OF_RANGE_FIXED_NUMBER.getMessage());
    }
}
