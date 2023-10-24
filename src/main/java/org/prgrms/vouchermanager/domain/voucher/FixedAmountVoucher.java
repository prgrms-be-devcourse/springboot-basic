package org.prgrms.vouchermanager.domain.voucher;



import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@RequiredArgsConstructor
@ToString
public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final int amount;
    private final VoucherType voucherType;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public VoucherType getType() {
        return voucherType;
    }

    @Override
    public int getAmount() {
        return amount;
    }


}
