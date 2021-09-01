package programmers.org.kdt.engine.voucher.type;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private static final long MAX_VOUCHER_AMOUNT = 10000;
    private final long amount;
    private final UUID voucherId;
    private final VoucherStatus voucherStatus = VoucherStatus.FixedAmountVoucher;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
        if(!conditionCheck()) {
            throw new IllegalArgumentException("Amount should be positive number");
        }
    }

    @Override
    public boolean conditionCheck () {
        return (this.amount > 0 && this.amount <= MAX_VOUCHER_AMOUNT);
    }

    @Override
    public VoucherStatus getVoucherStatus() {return voucherStatus;}

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return amount;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discounted = beforeDiscount - amount;
        return (discounted <= 0) ? 0 : discounted;
    }
}
