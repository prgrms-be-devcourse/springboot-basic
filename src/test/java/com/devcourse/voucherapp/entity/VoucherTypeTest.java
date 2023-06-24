package com.devcourse.voucherapp.entity;

import static com.devcourse.voucherapp.entity.VoucherType.getVoucherType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.devcourse.voucherapp.exception.VoucherInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VoucherTypeTest {

    @DisplayName("존재하는 할인권 방식을 입력했을 때, 해당 할인권 방식 객체가 반환되는지 테스트")
    @Test
    void selectExistedVoucherTypeTest() {
        VoucherType fixType = getVoucherType("1");
        VoucherType percentType = getVoucherType("2");

        assertEquals(VoucherType.FIX, fixType);
        assertEquals(VoucherType.PERCENT, percentType);
    }

    @DisplayName("존재하지 않는 할인권 방식을 입력했을 때, 예외처리 수행 테스트")
    @Test
    void selectNotExistedVoucherTypeTest() {
        assertThrows(VoucherInputException.class, () -> getVoucherType("10000"));
        assertThrows(VoucherInputException.class, () -> getVoucherType("string"));
    }

    @DisplayName("고정 금액 할인권을 생성할 때, 잘못된 금액을 입력한 경우 예외처리 수행 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "10.1", "string", "", " ", "\n"})
    void invalidFixDiscountPriceTest(String discountPrice) {
        assertThrows(VoucherInputException.class, () -> VoucherType.FIX.makeVoucher(discountPrice));
    }

    @DisplayName("비율 퍼센트 할인권을 생성할 때, 잘못된 퍼센트를 입력한 경우 예외처리 수행 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "10.1", "string", "101", "", " ", "\n"})
    void invalidPercentDiscountRateTest(String discountRate) {
        assertThrows(VoucherInputException.class, () -> VoucherType.PERCENT.makeVoucher(discountRate));
    }
}
