package org.prgms.voucherProgram.voucher.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final DiscountAmount discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount, LocalDateTime createdDateTime) {
        super(voucherId, createdDateTime);
        this.discountAmount = new DiscountAmount(discountAmount);
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, long discountAmount, LocalDateTime createdDateTime) {
        super(voucherId, customerId, createdDateTime);
        this.discountAmount = new DiscountAmount(discountAmount);
    }

    @Override
    public long discount(long beforeDiscount) {
        if (discountAmount.isBigger(beforeDiscount)) {
            return 0;
        }

        return discountAmount.discount(beforeDiscount);
    }

    @Override
    public int getType() {
        return VoucherType.FIXED_AMOUNT.getNumber();
    }

    @Override
    public long getDiscountValue() {
        return discountAmount.getAmount();
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "voucherId=" + voucherId +
            ", customerId=" + customerId +
            ", discountAmount=" + discountAmount +
            '}';
    }
}
