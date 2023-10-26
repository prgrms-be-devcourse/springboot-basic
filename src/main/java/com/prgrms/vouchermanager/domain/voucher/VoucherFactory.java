package com.prgrms.vouchermanager.domain.voucher;

import java.util.Optional;

public class VoucherFactory {
    public static Optional<Voucher> create(VoucherType voucherType, long discount) {
        if(voucherType == VoucherType.FIXED) {
            return Optional.of(new FixedAmountVoucher(discount));
        } else if(voucherType == VoucherType.PERCENT) {
            return Optional.of(new PercentAmountVoucher(discount));
        }
        return Optional.empty();
    }
}
