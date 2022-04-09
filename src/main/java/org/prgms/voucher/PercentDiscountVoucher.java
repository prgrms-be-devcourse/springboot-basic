package org.prgms.voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher{
    private final Long discountPercent;
    private final UUID voucherId;
    public PercentDiscountVoucher(Long discountPercent, UUID voucherId) {
        this.discountPercent = discountPercent;
        this.voucherId = voucherId;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return (long) ((1 - (discountPercent / 100.0)) * beforeDiscount);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }
}
