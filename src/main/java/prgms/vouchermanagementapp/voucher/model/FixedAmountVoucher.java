package prgms.vouchermanagementapp.voucher.model;

import prgms.vouchermanagementapp.io.model.Amount;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final Amount fixedDiscountAmount;

    public FixedAmountVoucher(UUID voucherId, Amount fixedDiscountAmount) {
        this.voucherId = voucherId;
        this.fixedDiscountAmount = fixedDiscountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long amountBeforeDiscount) {
        long discountAmount = this.fixedDiscountAmount.getAmount();
        if (amountBeforeDiscount < discountAmount) {
            throw new IllegalStateException(
                    MessageFormat.format("amount({0}) should be greater than discount amount({1}).",
                            amountBeforeDiscount,
                            discountAmount
                    )
            );
        }
        return amountBeforeDiscount - discountAmount;
    }

    public Amount getFixedDiscountAmount() {
        return this.fixedDiscountAmount;
    }
}
