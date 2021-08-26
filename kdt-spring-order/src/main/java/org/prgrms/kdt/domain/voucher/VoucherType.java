package org.prgrms.kdt.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Predicate;

public enum VoucherType {
    FIX(0, 100000000), PERCENT(1, 100);

    private final int value;
    private final int maxValue;

    VoucherType(int value, int maxValue) {
        this.value = value;
        this.maxValue = maxValue;
    }

    public int getValue() {
        return value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public static VoucherType findType(int value) {
        return Arrays.stream(VoucherType.values())
                .filter(isSameValue(value))
                .findFirst()
                .get();

    }
    public static int totalTypes() {
        return values().length;
    }

    public static boolean isInvalidType(int inputType) {
        return isUnderZero(inputType) || inputType >= totalTypes();
    }

    public static boolean isInvalidValueForType(long value, VoucherType type) {
        return isUnderZero(value) || isZero(value) || isOverMaxValue(value, type);
    }

    private static boolean isOverMaxValue(long value, VoucherType type) {
        return value > type.getMaxValue();
    }

    private static boolean isZero(long value) {
        return value == 0;
    }

    private static boolean isUnderZero(long value) {
        return value < 0;
    }

    private static Predicate<VoucherType> isSameValue(int type) {
        return v -> v.getValue() == type;
    }

}
