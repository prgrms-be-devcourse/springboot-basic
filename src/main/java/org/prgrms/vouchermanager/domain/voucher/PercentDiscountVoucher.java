package org.prgrms.vouchermanager.domain.voucher;



import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@RequiredArgsConstructor
@ToString
public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final int percent;
    private final VoucherType voucherType;

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        long resultPrice = beforeDiscount - ((beforeDiscount * percent) / 100);
        return resultPrice < 0? 0 : resultPrice;
    }

    @Override
    public VoucherType getType() {
        return voucherType;
    }

    @Override
    public int getAmount() {
        return percent;
    }

    public Voucher getVoucher(){
        return this;
    }
}
