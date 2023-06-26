package com.devcourse.voucherapp.entity;

import com.devcourse.voucherapp.entity.voucher.FixDiscountVoucher;
import com.devcourse.voucherapp.entity.voucher.PercentDiscountVoucher;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import com.devcourse.voucherapp.exception.VoucherInputException;
import java.util.Arrays;
import java.util.UUID;
import lombok.Getter;

public enum VoucherType {
    FIX("1", "고정 할인") {
        private static final String FIX_DISCOUNT_PRICE_REGEX = "^[1-9][0-9]*$";
        private static final String INVALID_FIX_DISCOUNT_PRICE_MESSAGE = "입력하신 금액이 조건에 맞지 않습니다.";

        @Override
        public Voucher makeVoucher(String discountPrice) {
            validateFixDiscountPrice(discountPrice);
            UUID voucherId = UUID.randomUUID();
            int fixDiscountPrice = Integer.parseInt(discountPrice);

            return new FixDiscountVoucher(voucherId, fixDiscountPrice);
        }

        private void validateFixDiscountPrice(String discountPrice) {
            if (!discountPrice.matches(FIX_DISCOUNT_PRICE_REGEX)) {
                throw new VoucherInputException(INVALID_FIX_DISCOUNT_PRICE_MESSAGE);
            }
        }
    },
    PERCENT("2", "비율 할인") {
        private static final String PERCENT_DISCOUNT_RATE_REGEX = "^[1-9]|[1-9][0-9]|100$";
        private static final String INVALID_PERCENT_DISCOUNT_RATE_MESSAGE = "입력하신 퍼센트가 조건에 맞지 않습니다.";

        @Override
        public Voucher makeVoucher(String discountRate) {
            validatePercentDiscountRate(discountRate);
            UUID voucherId = UUID.randomUUID();
            int percentDiscountRate = Integer.parseInt(discountRate);

            return new PercentDiscountVoucher(voucherId, percentDiscountRate);
        }

        private void validatePercentDiscountRate(String discountRate) {
            if (!discountRate.matches(PERCENT_DISCOUNT_RATE_REGEX)) {
                throw new VoucherInputException(INVALID_PERCENT_DISCOUNT_RATE_MESSAGE);
            }
        }
    };

    private static final String NOT_EXIST_VOUCHER_TYPE_MESSAGE = "입력하신 할인권 방식은 없는 방식입니다.";

    @Getter
    private final String number;

    private final String name;

    VoucherType(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public static VoucherType getVoucherType(String voucherTypeNumber) {
        return Arrays.stream(VoucherType.values())
            .filter(type -> voucherTypeNumber.equals(type.getNumber()))
            .findFirst()
            .orElseThrow(() -> new VoucherInputException(NOT_EXIST_VOUCHER_TYPE_MESSAGE));
    }

    public boolean isFix() {
        return this == VoucherType.FIX;
    }

    public boolean isPercent() {
        return this == VoucherType.PERCENT;
    }

    @Override
    public String toString() {
        return number + ". " + name;
    }

    public abstract Voucher makeVoucher(String discountAmount);
}
