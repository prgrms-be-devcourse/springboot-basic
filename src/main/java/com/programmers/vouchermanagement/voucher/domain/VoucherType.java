package com.programmers.vouchermanagement.voucher.domain;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum VoucherType {
    FIXED("1", "Fixed Amount", VoucherType::validateFixedAmount),
    PERCENT("2", "Percent", VoucherType::validatePercent);

    private static final Logger logger = LoggerFactory.getLogger(VoucherType.class);

    private static final int COMPARATOR_FLAG = 0;
    private static final BigDecimal MAX_PERCENT = BigDecimal.valueOf(100);

    private static final String INVALID_DISCOUNT_AMOUNT_MESSAGE =
            "Input should be a number greater than 0";
    private static final String INVALID_DISCOUNT_PERCENT_MESSAGE =
            "Input should be a number greater than 0 and smaller than 100";
    private static final String INVALID_VOUCHER_TYPE_MESSAGE =
            "Voucher type should be either fixed amount or percent discount voucher.";

    private final String menuCode;
    private final String typeName;
    private final Consumer<BigDecimal> validator;

    VoucherType(String menuCode, String typeName, Consumer<BigDecimal> validator) {
        this.menuCode = menuCode;
        this.typeName = typeName;
        this.validator = validator;
    }

    public static VoucherType findVoucherType(String input) {
        try {
            return VoucherType.findVoucherTypeByCode(input);
        } catch (IllegalArgumentException exception) {
            return VoucherType.findVoucherTypeByName(input);
        }
    }

    private static VoucherType findVoucherTypeByName(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(menu -> menu.isMatchingName(input))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error(INVALID_VOUCHER_TYPE_MESSAGE);
                    return new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE);
                });
    }

    private static VoucherType findVoucherTypeByCode(String input) {
        return Arrays.stream(VoucherType.values())
                .filter(menu -> menu.isMatchingCode(input))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error(INVALID_VOUCHER_TYPE_MESSAGE);
                    return new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE);
                });
    }

    private boolean isMatchingName(String input) {
        return input.equalsIgnoreCase(this.name());
    }

    private boolean isMatchingCode(String input) {
        return Objects.equals(menuCode, input);
    }

    public boolean isPercent() {
        return this == PERCENT;
    }

    public String displayTypeName() {
        return typeName;
    }

    public void validateDiscountValue(BigDecimal discountValue) {
        validator.accept(discountValue);
    }

    private static void validateFixedAmount(BigDecimal discountAmount) {
        if (discountAmount.compareTo(BigDecimal.ZERO) <= COMPARATOR_FLAG) {
            logger.error(INVALID_DISCOUNT_AMOUNT_MESSAGE);
            throw new IllegalArgumentException(INVALID_DISCOUNT_AMOUNT_MESSAGE);
        }
    }

    private static void validatePercent(BigDecimal discountPercent) {
        if (discountPercent.compareTo(BigDecimal.ZERO) <= COMPARATOR_FLAG || discountPercent.compareTo(MAX_PERCENT) > COMPARATOR_FLAG) {
            logger.error(INVALID_DISCOUNT_PERCENT_MESSAGE);
            throw new IllegalArgumentException(INVALID_DISCOUNT_PERCENT_MESSAGE);
        }
    }
}
