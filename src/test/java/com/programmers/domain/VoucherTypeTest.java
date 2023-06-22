package com.programmers.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class VoucherTypeTest {

    @DisplayName("입력된 숫자에 맞는 바우처를 찾는다")
    @Test
    void findVoucherTypeByNumber() {
        //given
        String input = "1";

        //when
        VoucherType result = VoucherType.findVoucherTypeByNumber(input);

        //then
        Assertions.assertThat(result).isEqualTo(VoucherType.FixedAmountVoucher);
    }

    @DisplayName("입력된 숫자가 비었을 경우 예외처리한다")
    @EmptySource
    @ParameterizedTest
    void findVoucherTypeByNumberWithEmptyInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.findVoucherTypeByNumber(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력된 숫자가 형식에 맞지 않는 경우 예외처리한다")
    @ValueSource(strings = {"ab", " ", "22", "c"})
    @ParameterizedTest
    void findVoucherTypeByNumberWithWrongInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.findVoucherTypeByNumber(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력된 이름에 맞는 바우처를 찾는다")
    @Test
    void findVoucherTypeByName() {
        //given
        String input = "percentdiscountvoucher";

        //when
        VoucherType result = VoucherType.findVoucherTypeByName(input);

        //then
        Assertions.assertThat(result).isEqualTo(VoucherType.PercentDiscountVoucher);
    }

    @DisplayName("입력된 이름이 비었을 경우 예외처리한다")
    @EmptySource
    @ParameterizedTest
    void findVoucherTypeByNameWithEmptyInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.findVoucherTypeByName(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력된 이름이 형식에 맞지 않는 경우 예외처리한다")
    @ValueSource(strings = {"ab", " ", "22", "c"})
    @ParameterizedTest
    void findVoucherTypeByNameWithWrongInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.findVoucherTypeByName(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}