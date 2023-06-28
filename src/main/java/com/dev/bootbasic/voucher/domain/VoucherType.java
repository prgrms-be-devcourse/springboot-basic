package com.dev.bootbasic.voucher.domain;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.dev.bootbasic.util.Constant.RANGE_SEPARATOR;
import static com.dev.bootbasic.util.Constant.SPACE_SEPARATOR;
import static java.lang.System.lineSeparator;

public enum VoucherType {

    FIXED(1_000, 100_000),
    PERCENT(1, 100)
    ;

    private static final String INVALID_VOUCHER_TYPE_MESSAGE = "생성할 수 없는 바우처 타입입니다.";
    private static final String INVALID_VALUE_MESSAGE = " Invalid value: ";
    private final int minimumAmount;
    private final int maximumAmount;

    VoucherType(int minimumAmount, int maximumAmount) {
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
    }

    public static VoucherType from(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE + INVALID_VALUE_MESSAGE + name);
        }
    }

    public static String getDisplayMessage() {
        return Arrays.stream(values())
                .map(voucher -> voucher.getAmountRange())
                .collect(Collectors.joining(lineSeparator()));
    }

    private String getAmountRange() {
        return name() + SPACE_SEPARATOR + minimumAmount + RANGE_SEPARATOR + maximumAmount;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public int getMaximumAmount() {
        return maximumAmount;
    }

}
