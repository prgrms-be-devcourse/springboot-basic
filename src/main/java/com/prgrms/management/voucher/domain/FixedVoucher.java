package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessageType;
import lombok.Getter;

@Getter
public class FixedVoucher extends Discount {
    private static final Long MAX_FIXED_DISCOUNT = 10000L;
    private static final Long MIN_FIXED_DISCOUNT = 0L;
    private final long amount;

    public FixedVoucher(long amount) {
        super();
        validateFixedAmount(amount);
        this.amount = amount;
    }

    private void validateFixedAmount(long amount) {
        if (amount < MIN_FIXED_DISCOUNT || amount > MAX_FIXED_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + ErrorMessageType.OUT_OF_RANGE_FIXED_NUMBER.getMessage());
    }
}
