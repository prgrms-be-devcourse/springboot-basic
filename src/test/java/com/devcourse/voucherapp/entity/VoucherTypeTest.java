package com.devcourse.voucherapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.devcourse.voucherapp.entity.voucher.VoucherType;
import com.devcourse.voucherapp.utils.exception.voucher.DiscountAmountException;
import com.devcourse.voucherapp.utils.exception.voucher.VoucherTypeInputException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VoucherTypeTest {

    @DisplayName("존재하는 할인권 방식 입력 시, 해당 할인권 방식 객체가 반환된다.")
    @Test
    void selectExistedVoucherTypeTest() {
        VoucherType fixType = VoucherType.from("1");
        VoucherType percentType = VoucherType.from("2");

        assertEquals(VoucherType.FIX, fixType);
        assertEquals(VoucherType.PERCENT, percentType);
    }

    @DisplayName("존재하지 않는 할인권 방식 입력 시, VoucherInputException 예외가 발생한다.")
    @Test
    void selectNotExistedVoucherTypeTest() {
        assertThrows(VoucherTypeInputException.class, () -> VoucherType.from("10000"));
        assertThrows(VoucherTypeInputException.class, () -> VoucherType.from("string"));
    }

    @DisplayName("고정 금액 할인권 생성 시, 잘못된 금액을 입력한 경우 VoucherInputException 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "10.1", "string", "", " ", "\n"})
    void invalidFixDiscountPriceTest(String discountPrice) {
        assertThrows(DiscountAmountException.class, () -> VoucherType.FIX.makeVoucher(UUID.randomUUID(), discountPrice));
    }

    @DisplayName("비율 퍼센트 할인권 생성 시, 잘못된 퍼센트를 입력한 경우 VoucherInputException 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "10.1", "string", "101", "", " ", "\n"})
    void invalidPercentDiscountRateTest(String discountRate) {
        assertThrows(DiscountAmountException.class, () -> VoucherType.PERCENT.makeVoucher(UUID.randomUUID(), discountRate));
    }
}
