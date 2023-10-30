package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;

import java.util.UUID;

@Getter
public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(long amount) {
        super(VoucherType.FIXED_AMOUNT, amount);
    }

    public FixedAmountVoucher(UUID id, long amount) {
        super(id, VoucherType.FIXED_AMOUNT, amount);
    }
}
