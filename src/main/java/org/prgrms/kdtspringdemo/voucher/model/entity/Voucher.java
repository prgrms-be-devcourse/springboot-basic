package org.prgrms.kdtspringdemo.voucher.model.entity;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

import java.util.UUID;

public interface Voucher {
    UUID getVoucherId();

    VoucherType getVoucherType();

    long getAmount();

    static Voucher create(VoucherType voucherType, long amount) {
        return switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(amount);
            case PERCENT -> new PercentAmountVoucher(amount);
        };
    }

    static Voucher update(UUID voucherId, VoucherType voucherType, long amount) {
        return switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(voucherId, amount);
            case PERCENT -> new PercentAmountVoucher(voucherId, amount);
        };
    }

    long executeDiscount(long originPrice);

    long validateAmount(long amount);
}
