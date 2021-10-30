package programmers.org.kdt.engine.voucher.type;

import java.util.UUID;

public class FixedAmountVoucher extends Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final long value;
    private final UUID voucherId;
    private final VoucherStatus voucherStatus = VoucherStatus.FIXEDAMOUNTVOUCHER;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.value = amount;
        if(!conditionCheck()) {
            throw new IllegalArgumentException("Amount should be positive number");
        }
    }

    @Override
    public boolean conditionCheck () {
        return (this.value > 0 && this.value <= MAX_VOUCHER_AMOUNT);
    }

    @Override
    public VoucherStatus getVoucherStatus() {return voucherStatus;}

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return value;
    }

    public long discount(long beforeDiscount) {
        var discounted = beforeDiscount - value;
        return (discounted <= 0) ? 0 : discounted;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "value=" + value +
            ", voucherId=" + voucherId +
            '}';
    }
}
