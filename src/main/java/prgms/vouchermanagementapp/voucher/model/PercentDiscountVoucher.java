package prgms.vouchermanagementapp.voucher.model;

import prgms.vouchermanagementapp.io.model.Ratio;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final Ratio fixedDiscountRatio;

    public PercentDiscountVoucher(UUID voucherId, Ratio fixedDiscountRatio) {
        this.voucherId = voucherId;
        this.fixedDiscountRatio = fixedDiscountRatio;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long amountBeforeDiscount) {
        return amountBeforeDiscount - (amountBeforeDiscount * (fixedDiscountRatio.getRatio() / 100));
    }

    public Ratio getFixedDiscountRatio() {
        return this.fixedDiscountRatio;
    }
}
