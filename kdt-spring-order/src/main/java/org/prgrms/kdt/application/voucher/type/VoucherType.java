package org.prgrms.kdt.application.voucher.type;

import org.prgrms.kdt.domain.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.domain.Voucher;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED("할인 금액") {
        @Override
        public Optional<Voucher> getVoucher(long value) {
            return Optional.of(new FixedAmountVoucher(UUID.randomUUID(), value));
        }
    },
    PERCENT("할인 비율 (1 ~100%)") {
        @Override
        public Optional<Voucher> getVoucher(long value) {
            if (value > 0 && value <= 100)
                return Optional.of(new PercentDiscountVoucher(UUID.randomUUID(), value));
            return Optional.empty();
        }
    };

    // TODO CommandType 과 동일한 구조이니 나중에 제네릭 타입으로 중복 제거 시도해보자.
    private static final Map<Integer, VoucherType> ORDINAL_TO_ENUM =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(Enum::ordinal, Function.identity())));

    private static final String LIST_OF_ENUM =
            ORDINAL_TO_ENUM.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.joining("\n"));

    private final String printString;

    VoucherType(String printString) {
        this.printString = printString;
    }

    public String getPrintString() {
        return printString;
    }

    public static Optional<VoucherType> fromOrdinal(int ordinal) {
        VoucherType voucherType = ORDINAL_TO_ENUM.get(ordinal);
        return Optional.ofNullable(voucherType);
    }

    public static String getListOfEnum() {
        return LIST_OF_ENUM;
    }

    public abstract Optional<Voucher> getVoucher(long value);
}
