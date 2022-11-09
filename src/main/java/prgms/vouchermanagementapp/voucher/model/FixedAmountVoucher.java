package prgms.vouchermanagementapp.voucher.model;

import prgms.vouchermanagementapp.model.Amount;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final Amount fixedDiscountAmount;

    public FixedAmountVoucher(UUID voucherId, Amount fixedDiscountAmount) {
        this.voucherId = voucherId;
        this.fixedDiscountAmount = fixedDiscountAmount;
    }

    @Override
    public long discount(long amountBeforeDiscount) {
        return amountBeforeDiscount - this.fixedDiscountAmount.getAmount();
    }
}
