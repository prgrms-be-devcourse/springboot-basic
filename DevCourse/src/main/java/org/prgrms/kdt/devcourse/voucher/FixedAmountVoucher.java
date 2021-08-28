package org.prgrms.kdt.devcourse.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private final UUID voucherId;
    private final long amount;
    private final VoucherType voucherType = VoucherType.FIXED;
    public FixedAmountVoucher(UUID voucherId, long amount) {
        if(amount<0)    throw new IllegalArgumentException("Amount should be positive");
        if(amount==0)    throw new IllegalArgumentException("Amount should not be zero");

        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getVoucherAmount() {
        return amount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountResult = beforeDiscount - amount;
        return (discountResult<0)? 0 : discountResult;
    }
}
