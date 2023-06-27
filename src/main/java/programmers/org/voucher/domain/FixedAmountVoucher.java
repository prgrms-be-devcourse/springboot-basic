package programmers.org.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final UUID voucherId;

    private final int discountAmount;

    public FixedAmountVoucher(UUID voucherId, int discountAmount) {
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public int discount(int amount) {
        return amount - discountAmount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
