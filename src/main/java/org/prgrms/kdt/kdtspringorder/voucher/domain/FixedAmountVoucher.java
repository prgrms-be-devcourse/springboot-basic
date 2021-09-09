package org.prgrms.kdt.kdtspringorder.voucher.domain;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher {

    private static final long MAX_VOUCHER_AMOUNT = 10000;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId, amount);
        super.voucherType = VoucherType.FIX;
        checkAmount(amount);
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, long amount) {
        super(voucherId, customerId, amount);
        super.voucherType = VoucherType.FIX;
        checkAmount(amount);
    }

    public FixedAmountVoucher(UUID voucherId, UUID customerId, VoucherType voucherType, long amount, boolean useYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        super(voucherId, customerId, voucherType, amount, useYn, createdAt, usedAt);
        checkAmount(amount);
    }

    private void checkAmount(long amount) {
        if(amount < 0) throw new IllegalArgumentException("Amount should be positive");
        if(amount == 0) throw new IllegalArgumentException("Amount should not be zero");
        if(amount > MAX_VOUCHER_AMOUNT) throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - super.getAmount();
        return ( discountedAmount < 0 ) ? 0 : discountedAmount;
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
            "voucherId=" + super.getVoucherId() +
            ", amount=" + super.getAmount() +
            '}';
    }

}
