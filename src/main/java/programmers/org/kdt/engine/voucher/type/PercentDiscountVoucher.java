package programmers.org.kdt.engine.voucher.type;

import java.util.UUID;

public class PercentDiscountVoucher extends Voucher {

    private final UUID voucherId;
    private final long value;
    private final VoucherStatus voucherStatus = VoucherStatus.PERCENTDISCOUNTVOUCHER;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        this.voucherId = voucherId;
        this.value = percent;
    }

    @Override
    public boolean conditionCheck() {
        return (this.value >= 0 && this.value <= 100);
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
        return value;
    }

    public long discount(long beforeDiscount) {
        return beforeDiscount * (value / 100);
    }
}
