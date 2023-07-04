package org.prgms.voucher.voucher;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class PercentDiscountVoucher implements Voucher {

    private final VoucherPolicy voucherPolicy = VoucherPolicy.PERCENT_DISCOUNT;
    private final long amount;
    private final UUID id;

    @Override
    public long discount(long price) {
        return price * (100 - amount) / 100;
    }
}
