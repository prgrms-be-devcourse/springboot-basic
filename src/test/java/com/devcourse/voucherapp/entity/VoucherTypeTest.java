package com.devcourse.voucherapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.devcourse.voucherapp.exception.VoucherInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VoucherTypeTest {

    @DisplayName("존재하는 할인권 방식 입력 시, 해당 할인권 방식 객체가 반환된다.")
    @Test
    void selectExistedVoucherTypeTest() {
        VoucherType fixType = VoucherType.of("1");
        VoucherType percentType = VoucherType.of("2");

        assertEquals(VoucherType.FIX, fixType);
        assertEquals(VoucherType.PERCENT, percentType);
    }

    @DisplayName("존재하지 않는 할인권 방식 입력 시, VoucherInputException 예외가 발생한다.")
    @Test
    void selectNotExistedVoucherTypeTest() {
        assertThrows(VoucherInputException.class, () -> VoucherType.of("10000"));
        assertThrows(VoucherInputException.class, () -> VoucherType.of("string"));
    }

    @DisplayName("고정 금액 할인권 생성 시, 잘못된 금액을 입력한 경우 VoucherInputException 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "10.1", "string", "", " ", "\n"})
    void invalidFixDiscountPriceTest(String discountPrice) {
        assertThrows(VoucherInputException.class, () -> VoucherType.FIX.makeVoucher(discountPrice));
    }

    @DisplayName("비율 퍼센트 할인권 생성 시, 잘못된 퍼센트를 입력한 경우 VoucherInputException 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "10.1", "string", "101", "", " ", "\n"})
    void invalidPercentDiscountRateTest(String discountRate) {
        assertThrows(VoucherInputException.class, () -> VoucherType.PERCENT.makeVoucher(discountRate));
    }
}
