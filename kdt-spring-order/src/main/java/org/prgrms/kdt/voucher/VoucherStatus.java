package org.prgrms.kdt.voucher;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherStatus {
    FIXED_AMOUNT("1", "FixedAmountVoucher"),
    PERCENT_DISCOUNT("2", "PercentDiscountVoucher");

    private final String number;
    private final String expression;

    VoucherStatus(String number, String expression) {
        this.number = number;
        this.expression = expression;
    }

    public static VoucherStatus of(String str) {
        return Stream.of(values())
                .filter(type -> type.number.equals(str))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("존재하지 Voucher 형식입니다."));
    }

    public static String getStringClassName(String num) {
        return Stream.of(values())
                .filter(type -> type.number.equals(num))
                .findFirst()
                .map(type -> type.expression)
                .orElseThrow(() -> new NoSuchElementException("존재하지 Voucher 형식입니다."));
    }

    public static String getAllVoucherExpression() {
        return Stream.of(values())
                .map(cmdStat -> cmdStat.number + ". " + cmdStat.expression)
                .collect(Collectors.joining("\n"));
    }
}
