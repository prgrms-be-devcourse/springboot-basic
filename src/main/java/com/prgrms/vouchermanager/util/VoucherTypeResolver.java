package com.prgrms.vouchermanager.util;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;

import java.util.Map;

public class VoucherTypeResolver {
    private static final Map<Class<? extends Voucher>, VoucherType> typeMap = Map.of(
            FixedAmountVoucher.class, VoucherType.FIXED,
            PercentAmountVoucher.class, VoucherType.PERCENT
    );

    public static VoucherType resolve(Voucher voucher) {
        return typeMap.get(voucher.getClass());
    }
}
