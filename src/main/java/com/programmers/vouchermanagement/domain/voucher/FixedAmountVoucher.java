package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FixedAmountVoucher extends Voucher {
    public FixedAmountVoucher(long amount) {
        super(VoucherType.FIXED_AMOUNT, amount);
    }

    public FixedAmountVoucher(UUID id, long amount) {
        super(id, VoucherType.FIXED_AMOUNT, amount);
    }

    public FixedAmountVoucher(long amount, LocalDateTime createdAt) {
        super(VoucherType.FIXED_AMOUNT, amount, createdAt);
    }

    public FixedAmountVoucher(UUID id, long amount, LocalDateTime createdAt) {
        super(id, VoucherType.FIXED_AMOUNT, amount, createdAt);
    }
}
