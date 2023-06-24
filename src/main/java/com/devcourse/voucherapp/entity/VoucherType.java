package com.devcourse.voucherapp.entity;

import com.devcourse.voucherapp.exception.VoucherInputException;
import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIX("1", "고정 할인") {
        private static final String FIX_DISCOUNT_PRICE_REGEX = "^[1-9][0-9]*$";

        @Override
        public Voucher makeVoucher(String discountPrice) {
            validateFixDiscountPrice(discountPrice);
            UUID voucherId = UUID.randomUUID();
            int fixDiscountPrice = Integer.parseInt(discountPrice);

            return new FixDiscountVoucher(voucherId, fixDiscountPrice);
        }

        private void validateFixDiscountPrice(String discountPrice) {
            if (!discountPrice.matches(FIX_DISCOUNT_PRICE_REGEX)) {
                throw new VoucherInputException(INVALID_DISCOUNT_AMOUNT_MESSAGE);
            }
        }
    },
    PERCENT("2", "비율 할인") {
        @Override
        public Voucher makeVoucher(String discountPercent) {
            return null;
        }
    };

    private static final String NOT_EXIST_VOUCHER_TYPE_MESSAGE = "입력하신 할인권 방식은 없는 방식입니다.";
    private static final String INVALID_DISCOUNT_AMOUNT_MESSAGE = "할인권의 금액 또는 퍼센트가 입력 조건에 맞지 않습니다.";

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

    public String getNumber() {
        return number;
    }

    public boolean isFix() {
        return this == VoucherType.FIX;
    }

    @Override
    public String toString() {
        return number + ". " + name;
    }

    public abstract Voucher makeVoucher(String discountAmount);
}
