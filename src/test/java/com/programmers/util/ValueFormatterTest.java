package com.programmers.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValueFormatterTest {

    @DisplayName("바우처 이름을 변형한다")
    @CsvSource(value = {
            "Fixed Amount Voucher : fixedamountvoucher",
            "Percent Discount Voucher : percentdiscountvoucher"
    }, delimiter = ':')
    @ParameterizedTest
    void reformatVoucherName(String input, String expected) {
        //given
        //when
        String result = ValueFormatter.reformatVoucherType(input);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("주어진 입력을 Long 타입으로 바꾸어 리턴한다")
    @Test
    void changeDiscountValueToNumber() {
        //given
        //when
        Long result = ValueFormatter.changeDiscountValueToNumber("123");

        //then
        Assertions.assertThat(result).isEqualTo(123L);
    }

    @DisplayName("주어진 입력이 숫자가 아니면 예외처리한다")
    @Test
    void changeNumberWithWrongInput() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> ValueFormatter.changeDiscountValueToNumber("abc"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}