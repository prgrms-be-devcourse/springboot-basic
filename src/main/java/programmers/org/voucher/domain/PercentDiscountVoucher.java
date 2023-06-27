package programmers.org.voucher.domain;

import programmers.org.voucher.constant.VoucherType;

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
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public int getDiscountAmount() {
        return discountRate;
    }
}
