package org.prgms.voucherProgram.domain.voucher;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private final DiscountAmount discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount) {
        super(voucherId);
        this.discountAmount = new DiscountAmount(discountAmount);
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, long discountAmount) {
        super(voucherId, customerId);
        this.discountAmount = new DiscountAmount(discountAmount);
    }

    @Override
    public long discount(long beforeDiscount) {
        if (discountAmount.isBiggerAmount(beforeDiscount)) {
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
