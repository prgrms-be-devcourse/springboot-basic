package org.prgrms.part1.engine;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FixedAmount("1") {
        @Override
        public Voucher createVoucher(UUID voucherId, Long value) {
            return new FixedAmountVoucher(voucherId, value);
        }
    }, PercentDiscount("2") {
        @Override
        public Voucher createVoucher(UUID voucherId, Long value) {
            return new PercentDiscountVoucher(voucherId, value);
        }
    };

    private final String code;

    public String getCode() {
        return code;
    }

    VoucherType(String code) {
        this.code = code;
    }

    public abstract Voucher createVoucher(UUID voucherId, Long value);

    public static Optional<VoucherType> findMatchingCode(String input) {
        return Optional.ofNullable(Arrays.stream(values()).filter(vt -> vt.code.equals(input)).findFirst().orElse(null));
    }
}
