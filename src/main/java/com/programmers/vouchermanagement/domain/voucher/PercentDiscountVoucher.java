package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class PercentDiscountVoucher implements Voucher {
    private UUID id;
    private final long amount;

    public PercentDiscountVoucher(long amount) {
        if(amount < 0 || amount > 100) {
            throw new IllegalArgumentException("Percent discount amount should be between 0 and 100");
        }
        this.amount = amount;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public VoucherType getType() {
        return VoucherType.PERCENT_DISCOUNT;
    }
}
