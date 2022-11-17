package org.prgrms.kdt.dao.entity.voucher;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FixedAmountVoucher"),
    PERCENT_DISCOUNT("2", "PercentDiscountVoucher");

    private final String typeNumber;
    private final String expression;

    VoucherType(String typeNumber, String expression) {
        this.typeNumber = typeNumber;
        this.expression = expression;
    }

    public static VoucherType of(String str) {
        return Stream.of(values())
                .filter(type -> type.typeNumber.equals(str))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 Voucher 형식입니다."));
    }

    public static String getStringClassName(String num) {
        return Stream.of(values())
                .filter(type -> type.typeNumber.equals(num))
                .findFirst()
                .map(type -> type.expression)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 Voucher 형식입니다."));
    }

    public static String getAllVoucherExpression() {
        return Stream.of(values())
                .map(cmdStat -> cmdStat.typeNumber + ". " + cmdStat.expression)
                .collect(Collectors.joining("\n"));
    }
}
