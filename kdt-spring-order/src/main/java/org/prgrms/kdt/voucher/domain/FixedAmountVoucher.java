package org.prgrms.kdt.voucher.domain;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if(amount <= 0) throw new IllegalArgumentException("amount가 0보다 작습니다");
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getValue() {
        return amount;
    }

    @Override
    public Enum<VoucherType> getVoucherType() {
        return VoucherType.FIXED;
    }

    public long discount(long beforeDiscount){
        var discountedAmount = beforeDiscount - amount;
        return (discountedAmount<0) ? 0 : discountedAmount;
    }


}
