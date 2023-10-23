package com.programmers.vouchermanagement.voucher.domain;

import javax.swing.plaf.PanelUI;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public enum VoucherType {
    FIXED("Fixed Amount", getFixedBiFunction(), validateDiscountAmount()),
    PERCENT("Percent", getPercentBiFunction(), validateDiscountPercent());

    private static final String INVALID_DISCOUNT_AMOUNT_MESSAGE =
            "Input should be a number greater than 0";
    private static final String INVALID_DISCOUNT_PERCENT_MESSAGE =
            "Input should be a number greater than 0 and smaller than 100";

    private final String typeName;
    private final BiFunction<BigDecimal, BigDecimal, BigDecimal> discountCalculator;
    private final Consumer<BigDecimal> validator;

    VoucherType(String typeName, BiFunction<BigDecimal, BigDecimal, BigDecimal> discountCalculator, Consumer<BigDecimal> validator) {
        this.typeName = typeName;
        this.discountCalculator = discountCalculator;
        this.validator = validator;
    }

    //set static to tell that this method does not depend on a particular Menu value
    public static Optional<VoucherType> findCreateMenu(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(menu -> menu.isMatching(input))
                .findFirst();
    }

    private static BiFunction<BigDecimal, BigDecimal, BigDecimal> getFixedBiFunction() {
        return (discountValue, priceBeforeDiscount) -> priceBeforeDiscount.subtract(discountValue);
    }

    private static BiFunction<BigDecimal, BigDecimal, BigDecimal> getPercentBiFunction() {
        return (discountValue, priceBeforeDiscount) -> {
            BigDecimal discounted = BigDecimal.valueOf(100).subtract(discountValue);
            return priceBeforeDiscount.multiply(discounted).divide(BigDecimal.valueOf(100), RoundingMode.FLOOR);
        };
    }

    private static Consumer<BigDecimal> validateDiscountAmount() {
        return (discountValue) -> {
            if (discountValue.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(INVALID_DISCOUNT_AMOUNT_MESSAGE);
            }
        };
    }

    private static Consumer<BigDecimal> validateDiscountPercent() {
        return (discountValue) -> {
            if (discountValue.compareTo(BigDecimal.ZERO) <= 0 || discountValue.compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new IllegalArgumentException(INVALID_DISCOUNT_PERCENT_MESSAGE);
            }
        };
    }

    public void validateDiscountValue(BigDecimal discountValue) {
        validator.accept(discountValue);
    }

    public BigDecimal discount(BigDecimal discountValue, BigDecimal priceBeforeDiscount) {
        return discountCalculator.apply(discountValue, priceBeforeDiscount);
    }

    private boolean isMatching(String input) {
        return Objects.equals(this.name().toLowerCase(), input);
    }

    public String displayTypeName() {
        return typeName;
    }
}
