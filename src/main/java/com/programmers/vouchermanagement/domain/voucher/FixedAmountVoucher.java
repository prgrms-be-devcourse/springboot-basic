package com.programmers.vouchermanagement.domain.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class FixedAmountVoucher implements Voucher {
    private final UUID id;
    private final long amount;

    @Override
    public VoucherType getType() {
        return VoucherType.FIXED_AMOUNT;
    }
}
