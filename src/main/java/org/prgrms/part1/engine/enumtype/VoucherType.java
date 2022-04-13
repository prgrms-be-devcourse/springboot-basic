package org.prgrms.part1.engine.enumtype;

import org.prgrms.part1.engine.domain.FixedAmountVoucher;
import org.prgrms.part1.engine.domain.PercentDiscountVoucher;
import org.prgrms.part1.engine.domain.Voucher;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FixedAmount("1", "amount") {
        @Override
        public Voucher createVoucher(UUID voucherId, Long value, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, value, createdAt);
        }
    }, PercentDiscount("2", "percent") {
        @Override
        public Voucher createVoucher(UUID voucherId, Long value, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, value, createdAt);
        }
    };

    private final String code;

    private final String valueColumnName;

    VoucherType(String code, String valueColumnName) {
        this.code = code;
        this.valueColumnName = valueColumnName;
    }

    public String getCode() {
        return code;
    }

    public String getValueColumnName() {
        return valueColumnName;
    }

    public abstract Voucher createVoucher(UUID voucherId, Long value, LocalDateTime createdAt);

    public static Optional<VoucherType> findMatchingCode(String input) {
        return Optional.ofNullable(Arrays.stream(values()).filter(vt -> vt.code.equals(input)).findFirst().orElse(null));
    }
}
