package com.devcourse.voucherapp.entity;

import static com.devcourse.voucherapp.entity.VoucherType.getVoucherType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.devcourse.voucherapp.exception.VoucherInputException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
