package programmers.org.voucher.domain;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherId;

    private final int discountRate;

    public PercentDiscountVoucher(UUID voucherId, int discountRate) {
        this.voucherId = voucherId;
        this.discountRate = discountRate;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public int discount(int amount) {
        return amount * discountRate / 100;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", discountRate=" + discountRate +
                '}';
    }
}
