package org.prgrms.kdt.dao.entity.voucher;

import org.prgrms.kdt.util.ValidatorUtil;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum VoucherType {
    FIXED_AMOUNT("1", "FixedAmountVoucher") {
        @Override
        public void validate(String discountAmount) {
            if (ValidatorUtil.isNumeric(discountAmount)) {
                Double discountValue = Double.parseDouble(discountAmount);

                if (discountValue != Math.floor(discountValue)) {
                    throw new IllegalArgumentException("할인 금액이 정수가 아닙니다.");
                } else if (discountValue < 0) {
                    throw new IllegalArgumentException("할인 금액이 음수입니다.");
                }
            } else {
                throw new IllegalArgumentException("할인금액이 숫자가 아닙니다.");
            }
        }
    },
    PERCENT_DISCOUNT("2", "PercentDiscountVoucher") {
        @Override
        public void validate(String discountAmount) {
            if (ValidatorUtil.isNumeric(discountAmount)) {
                Double discountValue = Double.parseDouble(discountAmount);

                if (discountValue <= MIN_PERCENT || discountValue > MAX_PERCENT) {
                    throw new IllegalArgumentException("할인율은 0보다 크고 100과 같거나 작아야 합니다.");
                }
            } else {
                throw new IllegalArgumentException("할인율이 숫자가 아닙니다.");
            }
        }
    };

    private static final double MIN_PERCENT = 0.0;
    private static final double MAX_PERCENT = 100.0;
    private final String typeNumber;
    private final String expression;

    VoucherType(String typeNumber, String expression) {
        this.typeNumber = typeNumber;
        this.expression = expression;
    }

    public static VoucherType of(String str) {
        return Stream.of(values())
                .filter(type -> type.typeNumber.equals(str) || type.expression.equals(str))
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

    public abstract void validate(String discountAmount);
}
