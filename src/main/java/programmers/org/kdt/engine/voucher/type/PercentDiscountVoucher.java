package programmers.org.kdt.engine.voucher.type;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;
    private final long percent;
    private final VoucherStatus voucherStatus = VoucherStatus.PercentDiscountVoucher;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.percent = percent;
    }

    @Override
    public boolean conditionCheck() {
        return (this.percent >= 0 && this.percent <= 100);
    }

    @Override
    public VoucherStatus getVoucherStatus() {
        return voucherStatus;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return percent;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}
