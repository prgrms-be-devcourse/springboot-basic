package org.prgms.voucher.voucher;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
@ToString
public class PercentDiscountVoucher implements Voucher {

    private final VoucherPolicy voucherPolicy = VoucherPolicy.PERCENT_DISCOUNT;
    private final long amount;
    private final UUID id;

    public PercentDiscountVoucher(long amount, UUID id) {
        this.amount = amount;
        this.id = id;
    }

    @Override
    public long discount(long price) {
        return price * (100 - amount) / 100;
    }
}
