package org.prgms.voucherProgram.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private final DiscountPercent discountPercent;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent, LocalDateTime createdDateTime) {
        super(voucherId, createdDateTime);
        this.discountPercent = new DiscountPercent(discountPercent);
    }

    public PercentDiscountVoucher(UUID voucherId, UUID customerId, long discountPercent,
        LocalDateTime createdDateTime) {
        super(voucherId, customerId, createdDateTime);
        this.discountPercent = new DiscountPercent(discountPercent);
    }

    @Override
    public long discount(long beforeDiscount) {
        return discountPercent.discount(beforeDiscount);
    }

    @Override
    public int getType() {
        return VoucherType.PERCENT_DISCOUNT.getNumber();
    }

    @Override
    public long getDiscountValue() {
        return discountPercent.getPercent();
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
            "voucherId=" + voucherId +
            ", customerId=" + customerId +
            ", discountPercent=" + discountPercent + "%" +
            '}';
    }
}
