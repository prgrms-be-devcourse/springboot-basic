package com.example.demo.domain.voucher;

import com.example.demo.enums.VoucherDiscountType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class VoucherTest {

    @DisplayName("고정된 값을 입력하여 고정 할인을 진행할 수 있다.")
    @ParameterizedTest
    @CsvSource({"1000, 200, 800", "2000, 500, 1500", "5000, 2300, 2700"})
    void proceedFixedDiscount(int itemPrice, int discountAmount, int expected) {
        //given
        Voucher voucher = new FixedAmountVoucher(discountAmount);

        //when
        int discountPrice = voucher.discount(itemPrice);

        //then
        Assertions.assertThat(discountPrice).isEqualTo(expected);
    }

    @DisplayName("0~100퍼센트 사이의 값을 받아 퍼센트 할인을 진행 할 수 있다.")
    @ParameterizedTest
    @CsvSource({"1000,10,900", "2000,50,1000", "5000, 25, 3750"})
    void proceedPercentDiscount(int itemPrice, int discountAmount, int expected) {
        //given
        Voucher voucher = new PercentDiscountVoucher(discountAmount);

        //when
        int discountPrice = voucher.discount(itemPrice);

        //then
        Assertions.assertThat(discountPrice).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"accVoucher", "divideVoucher"})
    @DisplayName("바우처 타입에 없는 타입이 입력되면 예외를 발생시킨다.")
    void voucherTypeTest(String input) {
        Assertions.assertThatThrownBy(() -> VoucherDiscountType.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력하신 " + input + "는 유효한 바우처 종류가 아닙니다.\n fix 또는 percent를 입력하세요.\n");
    }
}