package org.prgrms.vouchermanager.domain.voucher;



import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class PercentDiscountVoucher implements Voucher{
    private final UUID voucherId;
    private final long percent;

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
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
