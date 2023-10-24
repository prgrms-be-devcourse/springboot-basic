package com.programmers.vouchermanagement.voucher.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public enum VoucherType {
    FIXED("Fixed Amount", validateDiscountAmount()),
    PERCENT("Percent", validateDiscountPercent());

    //messages
    private static final String INVALID_DISCOUNT_AMOUNT_MESSAGE =
            "Input should be a number greater than 0";
    private static final String INVALID_DISCOUNT_PERCENT_MESSAGE =
            "Input should be a number greater than 0 and smaller than 100";
    private static final int COMPARATOR_FLAG = 0;
    private static final BigDecimal MAX_PERCENT = BigDecimal.valueOf(100);
    //---

    private final String typeName;
    private final Consumer<BigDecimal> validator;

    VoucherType(String typeName, Consumer<BigDecimal> validator) {
        this.typeName = typeName;
        this.validator = validator;
    }

    public static Optional<VoucherType> findCreateMenu(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(menu -> menu.isMatching(input))
                .findFirst();
    }

    private static Consumer<BigDecimal> validateDiscountAmount() {
        return (discountValue) -> {
            if (discountValue.compareTo(BigDecimal.ZERO) <= COMPARATOR_FLAG) {
                throw new IllegalArgumentException(INVALID_DISCOUNT_AMOUNT_MESSAGE);
            }
        };
    }

    private static Consumer<BigDecimal> validateDiscountPercent() {
        return (discountValue) -> {
            if (discountValue.compareTo(BigDecimal.ZERO) <= COMPARATOR_FLAG || discountValue.compareTo(MAX_PERCENT) > COMPARATOR_FLAG) {
                throw new IllegalArgumentException(INVALID_DISCOUNT_PERCENT_MESSAGE);
            }
        };
    }

    public void validateDiscountValue(BigDecimal discountValue) {
        validator.accept(discountValue);
    }

    private boolean isMatching(String input) {
        return Objects.equals(this.name().toLowerCase(), input);
    }

    public boolean isPercent() {
        return this == PERCENT;
    }

    public String displayTypeName() {
        return typeName;
    }
}
