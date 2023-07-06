package com.programmers.voucher.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

class VoucherTypeTest {

    @DisplayName("입력된 숫자에 맞는 바우처를 찾는다")
    @Test
    void findVoucherTypeByNumber() {
        //given
        String input = "1";

        //when
        VoucherType result = VoucherType.findVoucherType(input);

        //then
        assertThat(result, is(VoucherType.FixedAmountVoucher));
    }

    @DisplayName("입력된 이름에 맞는 바우처를 찾는다")
    @Test
    void findVoucherTypeByName() {
        //given
        String input = "percentdiscountvoucher";

        //when
        VoucherType result = VoucherType.findVoucherType(input);

        //then
        assertThat(result, is(VoucherType.PercentDiscountVoucher));
    }

    @DisplayName("입력된 값이 비었을 경우 예외처리한다")
    @EmptySource
    @ParameterizedTest
    void findVoucherTypeByNumberWithEmptyInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.findVoucherType(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력된 값이 형식에 맞지 않는 경우 예외처리한다")
    @ValueSource(strings = {"ab", " ", "22", "c"})
    @ParameterizedTest
    void findVoucherTypeByNumberWithWrongInput(String input) {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> VoucherType.findVoucherType(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("타입에 맞는 바우처를 생성한다")
    @Test
    void constructVoucherWithUUID() {
        //given
        UUID uuid = UUID.randomUUID();
        String voucherTypeInput = "percentdiscountvoucher";
        String voucherName = "voucher name for test";
        Long discountValue = 10L;

        //when
        Voucher result = VoucherType.createVoucher(voucherTypeInput, uuid, voucherName, discountValue);

        //then
        assertThat(result.getVoucherId(), is(uuid));
    }
}