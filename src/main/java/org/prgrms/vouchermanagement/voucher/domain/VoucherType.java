package org.prgrms.vouchermanagement.voucher.domain;

import org.prgrms.vouchermanagement.exception.voucher.InCorrectVoucherTypeException;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

/*
* 여러 Voucher 의 타입에 대한 값들을 정의한 enum 클래스입니다.
* 사용자의 입력값으로 받을 Voucher 의 타입, 최댓값, 바우처의 생성 함수를 필드로 가집니다.
* 바우처 생성, 입력값에 따른 바우처 타입 찾기, 특정 바우처에 대한 입력값 검증의 기능을 갖습니다. (너무 많은 책임을 갖는지 의문입니다.)
* */
public enum VoucherType {

    FIXED_AMOUNT("1", 1000000, FixedAmountVoucher::new),
    PERCENT_DISCOUNT("2", 100, PercentDiscountVoucher::new),;

    private final String voucherTypeNumber;
    private final int maximumDiscountAmount;
    private final BiFunction<UUID, Integer, Voucher> voucherConstructor;

    VoucherType(String voucherTypeNumber, int maximumDiscountAmount, BiFunction<UUID, Integer, Voucher> voucherConstructor) {
        this.voucherTypeNumber = voucherTypeNumber;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.voucherConstructor = voucherConstructor;
    }

    public static Voucher createVoucher(UUID uuid, String voucherTypeInput, int discountValue) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.isMatchVoucherType(voucherTypeInput))
                .findAny()
                .orElseThrow(InCorrectVoucherTypeException::new)
                .voucherConstructor.apply(uuid, discountValue);
    }

    public boolean isMatchVoucherType(String voucherTypeInput) {
        return voucherTypeNumber.equals(voucherTypeInput) || name().equals(voucherTypeInput);
    }

    public static boolean isCorrectVoucherType(String voucherTypeInput) {
        return Arrays.stream(values())
                .anyMatch(voucherType -> voucherType.isMatchVoucherType(voucherTypeInput));
    }

    public static boolean isCorrectDiscountAmount(String voucherTypeNumberInput, int discountAmountInput) {
        return Arrays.stream(values())
                .filter(voucherTypeNumber -> voucherTypeNumber.isMatchVoucherType(voucherTypeNumberInput))
                .findFirst()
                .orElseThrow(InCorrectVoucherTypeException::new)
                .checkDiscountAmountRange(discountAmountInput);
    }

    private boolean checkDiscountAmountRange(int discountAmountInput) {
        return discountAmountInput >= 0 && discountAmountInput <= maximumDiscountAmount;
    }
}
