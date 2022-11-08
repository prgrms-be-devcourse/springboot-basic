package com.programmers.voucher.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.programmers.voucher.voucher.VoucherType.FixedAmount;
import static com.programmers.voucher.voucher.VoucherType.PercentDiscount;
import static com.programmers.voucher.voucher.VoucherValidator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VoucherValidatorTest {

    @Test
    @DisplayName("올바른 타입과 한도 내의 금액을 입력한 경우 검증 통과")
    void validatorTest() {
        String typeF = FixedAmount.getType();
        String valueF = "2000";

        String typeP = FixedAmount.getType();
        String valueP = "2000";

        boolean validateValue = VoucherValidator.isValidateValue(typeF, valueF);
        boolean validateValue2 = VoucherValidator.isValidateValue(typeP, valueP);

        assertEquals(true, validateValue);
        assertEquals(true, validateValue2);
    }


    @Test
    @DisplayName("value로 입력한 값이 숫자인 경우 검증 통과")
    void isNumericTest1() {
        boolean validateValue = VoucherValidator.isNumeric("1259764");
        assertEquals(true, validateValue);
    }

    @Test
    @DisplayName("value로 입력한 값이 숫자가 아닌 경우 false 리턴")
    void isNumericTest2() {
        boolean validateValue = VoucherValidator.isNumeric("asdf");
        assertEquals(false, validateValue);
    }

    @Test
    @DisplayName("바우처의 value 입력값으로 MAX경계값을 입력하는 경우 true 리턴")
    void isProperValue() {
        String typeP = PercentDiscount.getType();
        boolean validateValue = VoucherValidator.isProperValue(typeP, String.valueOf(MAX_DISCOUNT_PERCENTAGE));

        String typeF = FixedAmount.getType();
        boolean validateValue2 = VoucherValidator.isProperValue(typeF, String.valueOf(MAX_DISCOUNT_COST));

        assertEquals(true, validateValue);
        assertEquals(true, validateValue2);
    }

    @Test
    @DisplayName("바우처의 value 입력값으로 MIN경계값을 입력하는 경우 true 리턴")
    void isProperValue2() {
        String typeP = PercentDiscount.getType();
        boolean validateValue = VoucherValidator.isProperValue(typeP, String.valueOf(MIN_DISCOUNT_PERCENTAGE));

        String typeF = FixedAmount.getType();
        boolean validateValue2 = VoucherValidator.isProperValue(typeF, String.valueOf(MIN_DISCOUNT_COST));

        assertEquals(true, validateValue);
        assertEquals(true, validateValue2);
    }

    @Test
    @DisplayName("바우처의 타입이 FixDiscount일 때  value 입력값으로 1000 ~ 200000을 벗어나면 false 리턴")
    void isProperValue3() {
        String typeF = FixedAmount.getType();

        int smallerThanMinValue = MIN_DISCOUNT_COST - 1; // 999
        boolean validateValue = VoucherValidator.isProperValue(typeF, String.valueOf(smallerThanMinValue));

        int biggerThanMaxValue = MAX_DISCOUNT_COST + 1;
        boolean validateValue2 = VoucherValidator.isProperValue(typeF, String.valueOf(biggerThanMaxValue));

        assertEquals(false, validateValue);
        assertEquals(false, validateValue2);
    }

    @Test
    @DisplayName("바우처의 타입이 PercentDiscount일 때  value 입력값으로 1~100을 벗어나면 false 리턴")
    void isProperValue4() {
        String typeP = PercentDiscount.getType();
        int biggerThanMaxValue = MAX_DISCOUNT_PERCENTAGE + 1; // 101
        boolean validateValue = VoucherValidator.isProperValue(typeP, String.valueOf(biggerThanMaxValue));

        int smallerThanMinValue = MIN_DISCOUNT_PERCENTAGE - 1; // 0
        boolean validateValue2 = VoucherValidator.isProperValue(typeP, String.valueOf(smallerThanMinValue));

        assertEquals(false, validateValue);
        assertEquals(false, validateValue2);
    }
}