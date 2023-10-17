package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class FixedAmountVoucher implements Voucher {
    private UUID id;
    private final long amount;

    public FixedAmountVoucher(long amount) {
        this.amount = amount;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public VoucherType getType() {
        return VoucherType.FIXED_AMOUNT;
    }
}
