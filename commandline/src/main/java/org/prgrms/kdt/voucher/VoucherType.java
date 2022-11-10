package org.prgrms.kdt.voucher;

import org.prgrms.kdt.exception.NotFindVoucherType;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FixedAmountVoucher"),
    PERCENTAGE("2", "PercentDiscountVoucher");

    private final String typeValue;
    private final String expression;

    private static final String SEPARATOR = ". ";

    VoucherType(String typeValue, String expression) {
        this.typeValue = typeValue;
        this.expression = expression;
    }

    public static VoucherType selectVoucherType(String type) {
        return Stream.of(values())
                .filter(voucherType -> voucherType.typeValue.equals(type))
                .findFirst()
                .orElseThrow(() -> new NotFindVoucherType());
    }

    public static String getAllVoucherExpression() {
        return Stream.of(values())
                .map(type -> type.typeValue + SEPARATOR + type.expression)
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
