package programmers.org.voucher.domain;

import programmers.org.voucher.constant.VoucherType;

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
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public int getDiscountAmount() {
        return discountAmount;
    }
}
