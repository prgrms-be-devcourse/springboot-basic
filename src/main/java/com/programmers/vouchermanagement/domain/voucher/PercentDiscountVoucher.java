package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class PercentDiscountVoucher implements Voucher {
    private final UUID id;
    private final long amount;

    public PercentDiscountVoucher(UUID id, long amount) {
        if(amount < 0 || amount > 100) {
            throw new IllegalArgumentException("Percent discount amount should be between 0 and 100");
        }

        this.id = id;
        this.amount = amount;
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PERCENT_DISCOUNT;
    }
}
