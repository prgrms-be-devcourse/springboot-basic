package prgms.vouchermanagementapp.domain;

import prgms.vouchermanagementapp.domain.value.Ratio;

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

    @Override
    public long getFixedDiscountLevel() {
        return this.fixedDiscountRatio.getRatio();
    }
}
