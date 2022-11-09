package com.programmers.voucher.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;
import static com.programmers.voucher.voucher.VoucherValidator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoucherValidatorTest {

    @Test
    @DisplayName("바우처의 value가 숫자가 아닌 경우 런타임 예외가 발생한다.")
    void isNumeric() {
        assertThrows(RuntimeException.class,
                () -> VoucherValidator.isNumeric("hello World"));
    }

    @Test
    @DisplayName("바우처의 value가 숫자면 검증 통과")
    void isNumeric2() {
        boolean result1 = VoucherValidator.isNumeric("98347598374");
        boolean result2 = VoucherValidator.isNumeric("123582975");
        boolean result3 = VoucherValidator.isNumeric("8588585858585885858");

        assertEquals(true, result1);
        assertEquals(true, result2);
        assertEquals(true, result3);
    }

    @Test
    @DisplayName("한도 내의 금액을 입력한 경우 검증 통과")
    void 한도금액테스트() {
        VoucherType typeF = FixedAmount;
        String valueF = "2000";

        VoucherType typeP = PercentDiscount;
        String valueP = "50";

        boolean resultF = isProperValue(typeF, valueF);
        boolean resultP = isProperValue(typeP, valueP);

        assertEquals(true, resultF);
        assertEquals(true, resultP);
    }

    @Test
    @DisplayName("FixedAmount 타입에 한도 금액의 MAX 경계값을 입력한 경우 검증 통과")
    void 한도금액테스트2() {
        boolean result = isProperValue(FixedAmount, String.valueOf(MAX_DISCOUNT_COST));
        assertEquals(true, result);
    }

    @Test
    @DisplayName("FixedAmount 타입에 한도 금액의 MIN 경계값을 입력한 경우 검증 통과")
    void 한도금액테스트3() {
        boolean result = isProperValue(FixedAmount, String.valueOf(MIN_DISCOUNT_COST));
        assertEquals(true, result);
    }

    @Test
    @DisplayName("Percent 타입에 한도 금액의 MAX 경계값을 입력한 경우 검증 통과")
    void 한도금액테스트4() {
        boolean result = isProperValue(PercentDiscount, String.valueOf(MAX_DISCOUNT_PERCENTAGE));
        assertEquals(true, result);
    }

    @Test
    @DisplayName("Percent 타입에 한도 금액의 MIN 경계값을 입력한 경우 검증 통과")
    void 한도금액테스트5() {
        boolean result = isProperValue(PercentDiscount, String.valueOf(MIN_DISCOUNT_PERCENTAGE));
        assertEquals(true, result);
    }

    @Test
    @DisplayName("FixedAmount 타입에 한도 금액을 벗어나는 금액을 입력한 경우 런타임 예외가 발생한다.")
    void 한도금액초과테스트() {
        int biggerThanMaxValue = MAX_DISCOUNT_COST + 1;
        int smallerThanMinValue = MIN_DISCOUNT_COST - 1;


        assertThrows(RuntimeException.class,
                () -> ValidateValue(FixedAmount, String.valueOf(biggerThanMaxValue)));

        assertThrows(RuntimeException.class,
                () -> ValidateValue(FixedAmount, String.valueOf(smallerThanMinValue)));
    }

    @Test
    @DisplayName("Percent 타입에 한도 금액을 벗어나는 금액을 입력한 경우 런타임 예외가 발생한다.")
    void 한도금액초과테스트2() {
        int biggerThanMaxValue = MAX_DISCOUNT_PERCENTAGE + 1;
        int smallerThanMinValue = MIN_DISCOUNT_PERCENTAGE - 1;


        assertThrows(RuntimeException.class,
                () -> ValidateValue(PercentDiscount, String.valueOf(biggerThanMaxValue)));

        assertThrows(RuntimeException.class,
                () -> ValidateValue(PercentDiscount, String.valueOf(smallerThanMinValue)));
    }

    @Test
    @DisplayName("정상적인 타입을 입력한 경우 VoucherType을 반환한다.")
    void 바우처타입검색2() {
        VoucherType fixedType = getValidateVoucherType("F");
        assertThat(fixedType).isInstanceOf(VoucherType.class);

        VoucherType percentType = getValidateVoucherType("P");
        assertThat(percentType).isInstanceOf(VoucherType.class);
    }

    @Test
    @DisplayName("정상적인 타입을 입력한 경우 VoucherType을 반환한다.")
    void 바우처타입검색3() {
        VoucherType fixedType = getValidateVoucherType("FixedAmount");
        assertThat(fixedType).isInstanceOf(VoucherType.class);

        VoucherType percentType = getValidateVoucherType("PercentDiscount");
        assertThat(percentType).isInstanceOf(VoucherType.class);
    }
}