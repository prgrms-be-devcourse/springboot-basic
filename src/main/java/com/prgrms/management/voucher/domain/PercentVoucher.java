package com.prgrms.management.voucher.domain;

import com.prgrms.management.config.ErrorMessageType;
import lombok.Getter;

@Getter
public class PercentVoucher extends Discount {
    private static final Long MAX_PERCENT_DISCOUNT = 100L;
    private static final Long MIN_PERCENT_DISCOUNT = 0L;
    private final long amount;

    public PercentVoucher(long amount) {
        super();
        validatePercentAmount(amount);
        this.amount = amount;
    }

    private void validatePercentAmount(long amount) {
        if (amount < MIN_PERCENT_DISCOUNT || amount > MAX_PERCENT_DISCOUNT)
            throw new NumberFormatException(VoucherType.class + ErrorMessageType.OUT_OF_RANGE_PERCENT_NUMBER.getMessage());
    }
}
