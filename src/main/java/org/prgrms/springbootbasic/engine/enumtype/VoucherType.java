package org.prgrms.springbootbasic.engine.enumtype;

import org.prgrms.springbootbasic.engine.domain.FixedAmountVoucher;
import org.prgrms.springbootbasic.engine.domain.PercentDiscountVoucher;
import org.prgrms.springbootbasic.engine.domain.Voucher;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "amount") {
        @Override
        public Voucher createVoucher(UUID voucherId, Integer value, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, value, createdAt);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, UUID customerID, Integer value, LocalDateTime createdAt) {
            return new FixedAmountVoucher(voucherId, customerID, value, createdAt);
        }
    }, PERCENT_DISCOUNT("2", "percent") {
        @Override
        public Voucher createVoucher(UUID voucherId, Integer value, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, value, createdAt);
        }

        @Override
        public Voucher createVoucher(UUID voucherId, UUID customerID, Integer value, LocalDateTime createdAt) {
            return new PercentDiscountVoucher(voucherId, customerID, value, createdAt);
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

    public abstract Voucher createVoucher(UUID voucherId, Integer value, LocalDateTime createdAt);

    public abstract Voucher createVoucher(UUID voucherId, UUID customerID, Integer value, LocalDateTime createdAt);

    private static final Map<String, VoucherType> map = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(VoucherType::getCode, Function.identity())));

    public static Optional<VoucherType> findMatchingCode(String input) {
        return Optional.ofNullable(map.get(input));
    }
}
