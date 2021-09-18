package org.prgrms.dev.voucher.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED("fixed") {
        @Override
        public Voucher create(UUID voucherId, long value, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, value, createdAt);
        }
    },
    PERCENT("percent") {
        @Override
        public Voucher create(UUID voucherId, long value, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, value, createdAt);
        }
    };

    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static Voucher getVoucherType(String inputType, UUID voucherId, long value, LocalDateTime createdAt) {
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equalsIgnoreCase(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid voucher type input..."));

        return voucherType.create(voucherId, value, createdAt);
    }

    public abstract Voucher create(UUID voucherId, long value, LocalDateTime createdAt);
}
