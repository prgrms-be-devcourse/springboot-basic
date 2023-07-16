package org.prgrms.kdt.storage;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherKind {
    FIXED_AMOUNT_VOUCHER("FixedAmountVoucher"), PERCENT_DISCOUNT_VOUCHER("PercentDiscountVoucher");

    private final String voucher;
    private static final Map<String, VoucherKind> VOUCHER_KIND_MAP = Stream.of(values())
            .collect(Collectors.toMap(voucherKind -> voucherKind.voucher, voucherKind -> voucherKind));

    VoucherKind(String voucher) {
        this.voucher = voucher;
    }

    public static VoucherKind findVoucherKindByFile(String voucher) {
        return VOUCHER_KIND_MAP.get(voucher);
    }
}
