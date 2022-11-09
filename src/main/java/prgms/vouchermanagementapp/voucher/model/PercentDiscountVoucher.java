package prgms.vouchermanagementapp.voucher.model;

import prgms.vouchermanagementapp.model.Ratio;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final Ratio ratio;

    public PercentDiscountVoucher(UUID voucherId, Ratio ratio) {
        this.voucherId = voucherId;
        this.ratio = ratio;
    }

    @Override
    public UUID getVoucherId() {
        return this.voucherId;
    }

    @Override
    public long discount(long amountBeforeDiscount) {
        return amountBeforeDiscount - (amountBeforeDiscount * (ratio.getRatio() / 100));
    }
}
