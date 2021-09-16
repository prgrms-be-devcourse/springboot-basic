package org.prgrms.kdt.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;
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
        return this.value;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public static VoucherType of(int value) {
        return Arrays.stream(VoucherType.values())
                .filter(isSameValue(value))
                .findFirst()
                .get();

    }

    public static VoucherType of(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.toString().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        MessageFormat.format("해당 바우처가 존재하지 않습니다. voucher = {0}", type)));
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
