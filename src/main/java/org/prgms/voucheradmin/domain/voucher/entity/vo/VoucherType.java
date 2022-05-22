package org.prgms.voucheradmin.domain.voucher.entity.vo;

import org.apache.commons.lang3.function.TriFunction;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FIXED_AMOUNT", (uuid, amount, createdAt) -> new FixedAmountVoucher(uuid, amount, createdAt)),
    PERCENTAGE_DISCOUNT("2", "PERCENTAGE_DISCOUNT", (uuid, amount, createdAt) -> new PercentageDiscountVoucher(uuid, amount.intValue(), createdAt));

    private final String voucherTypeId;
    private final String typeName;
    private TriFunction<UUID, Long, LocalDateTime, Voucher> createVoucher;
    private static final Map<String, VoucherType> voucherTypes =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(voucherType -> voucherType.voucherTypeId, Function.identity())));

    VoucherType(String voucherTypeId, String typeName, TriFunction<UUID, Long, LocalDateTime, Voucher> createVoucher) {
        this.voucherTypeId = voucherTypeId;
        this.typeName = typeName;
        this.createVoucher = createVoucher;
    }

    public String getTypeName() {
        return typeName;
    }

    public Voucher createVoucher(UUID uuid, long amount, LocalDateTime createdAt) {
        return createVoucher.apply(uuid, amount, createdAt);
    }

    @Override
    public String toString() {
        return String.format("%s. %s", voucherTypeId, typeName);
    }

    public static Optional<VoucherType> findVoucherType(String selectedVoucherTypeId) {
        return Optional.ofNullable(voucherTypes.get(selectedVoucherTypeId));
    }
}
