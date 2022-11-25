package com.programmers.voucher.model.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherTypeTest {
    @Test
    @DisplayName("문자열을 받아서 일치하는 바우처타입을 반환한다.")
    void convertToVoucherType() {
        //given
        String inputString = "1";

        //when
        VoucherType result = VoucherType.toVoucherType(inputString);

        //then
        assertThat(result.getType()).isEqualTo("1");
    }

    @Test
    @DisplayName("바우처타입에 해당하는 바우처를 생성한다.")
    void convertToVoucher() {
        //given
        VoucherType voucherType = VoucherType.toVoucherType("1");

        //when
        Voucher result = voucherType.convertToVoucher(UUID.randomUUID(), 1000);

        //then
        assertThat(result.getDiscountValue()).isEqualTo(1000);
    }

    @Test
    @DisplayName("주어진 바우처타입 외에 문자열을 입력하면 예외를 발생시킨다.")
    void cannotConvertToVoucherType() {
        assertThatThrownBy(() -> VoucherType.toVoucherType("3"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
