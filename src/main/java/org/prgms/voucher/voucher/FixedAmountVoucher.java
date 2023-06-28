package org.prgms.voucher.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class FixedAmountVoucher implements Voucher {

    private final long amount;
    private final UUID id;
    private final VoucherPolicy voucherPolicy = VoucherPolicy.FIXED_AMOUNT;

    @Override
    public long discount(long price) {
        return Math.max(0, price - amount);
    }

}
