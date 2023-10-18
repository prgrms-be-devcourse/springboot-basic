package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.io.VoucherType;

import java.util.Optional;

public class VoucherFactory {
    public static Optional<Voucher> createVoucher(VoucherType voucherType, long discount) {
        if(voucherType == VoucherType.FIXED) {
            return Optional.of(new FixedAmountVoucher(discount));
        } else if(voucherType == VoucherType.PERCENT) {
            return Optional.of(new PercentAmountVoucher(discount));
        }
        return Optional.empty();
    }
}
