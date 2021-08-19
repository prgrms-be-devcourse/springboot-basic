package programmers.org.kdt.engine.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final long amount;
    private final UUID voucherId;
    private final VoucherStatus voucherStatus = VoucherStatus.FixedAmountVoucher;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    //eunu fix
    public boolean conditionCheck () {
        return this.amount >= 0;
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
        return beforeDiscount - amount;
    }
}
