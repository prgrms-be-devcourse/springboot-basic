package com.prgrms.management.voucher.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.prgrms.management.config.ErrorMessageType.OUT_OF_RANGE_PERCENT_NUMBER;

@Getter
public class PercentVoucher extends Voucher {
    private static final long MAX_PERCENT_DISCOUNT = 100L;
    private static final long MIN_PERCENT_DISCOUNT = 0L;
    private final long amount;
    private final VoucherType voucherType;

    public PercentVoucher(long amount, VoucherType voucherType, UUID customerId) {
        super(customerId);
        validatePercentAmount(amount);
        this.amount = amount;
        this.voucherType = voucherType;
    }

    public PercentVoucher(UUID voucherId, LocalDateTime createdAt, UUID customerId, long amount, VoucherType voucherType) {
        super(voucherId, createdAt, customerId);
        validatePercentAmount(amount);
        this.amount = amount;
        this.voucherType = voucherType;
    }

    private void validatePercentAmount(long amount) {
        if (amount < MIN_PERCENT_DISCOUNT || amount > MAX_PERCENT_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + OUT_OF_RANGE_PERCENT_NUMBER.getMessage());
    }
}
