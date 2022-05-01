package org.prgrms.kdt.voucher.domain;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED("fixed") {
        @Override
        public Voucher create(UUID voucherId, int value) {
            return new FixedAmountVoucher(voucherId, value);
        }
    },
    PERCENT("percent") {
        @Override
        public Voucher create(UUID voucherId, int value) {
            return new PercentDiscountVoucher(voucherId, value);
        }
    };

    private final String type;

    VoucherType(String type) { this.type = type; }

    public abstract Voucher create(UUID voucherId, int value);

    public static Voucher getVoucherType(String inputType, UUID voucherId, int discountAmount) {
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equalsIgnoreCase(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no '" + inputType + "'. "));
        return voucherType.create(voucherId, discountAmount);
    }

    public static void checkVoucherType(String inputType) {
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equalsIgnoreCase(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no '" + inputType + "'. "));
    }
}
