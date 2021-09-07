package prgms.springbasic.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }

    @Override
    public long getDiscountValue() {
        return amount;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "  discount: -"
                + getDiscountValue() + "원  voucherId: "
                + getVoucherId();
    }

}
