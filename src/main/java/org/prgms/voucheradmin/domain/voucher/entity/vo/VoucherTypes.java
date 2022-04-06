package org.prgms.voucheradmin.domain.voucher.entity.vo;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherTypes {
    FIXED_AMOUNT("1", "FixedAmountVoucher"),
    PERCENTAGE_DISCOUNT("2", "PercentDiscountVoucher");

    private final String voucherTypeId;
    private final String typeName;
    private static final Map<String, VoucherTypes> voucherTypes =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(voucherType -> voucherType.voucherTypeId, Function.identity())));

    VoucherTypes(String voucherTypeId, String typeName) {
        this.voucherTypeId = voucherTypeId;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String toString() {
        StringBuilder voucherTypeStrBuilder = new StringBuilder();
        voucherTypeStrBuilder.append(voucherTypeId).append(". ").append(typeName);
        return voucherTypeStrBuilder.toString();
    }

    public static Optional<VoucherTypes> findVoucherType(String selectedVoucherTypeId) {
        return Optional.ofNullable(voucherTypes.get(selectedVoucherTypeId));
    }
}
