package com.prgrms.springbootbasic.voucher;

import com.prgrms.springbootbasic.common.exception.InvalidVoucherTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class VoucherTypeTest {

    @ParameterizedTest
    @CsvSource({"fixedAmount,FIXED_AMOUNT", "percent,PERCENT"})
    @DisplayName("사용자의 Voucher 타입 입력에 따라 Voucher Type을 정할 수 있다")
    void findCommandByInputValue(String voucherTypeInput, VoucherType expectedVoucherType) {
        //given&when
        VoucherType voucherType = VoucherType.fromInputValue(voucherTypeInput);

        //then
        assertThat(voucherType).isEqualTo(expectedVoucherType);
    }

    @Test
    @DisplayName("사용자가 잘못된 Voucher 타입을 입력하면 예외를 발생시킨다")
    void invalidCommandByInputValue() {
        //given
        String invalidInput = "invalid-voucher-type";

        //when&then
        Assertions.assertThrows(InvalidVoucherTypeException.class, () -> VoucherType.fromInputValue(invalidInput));
    }

    @Test
    @DisplayName("사용자가 잘못된 Voucher 타입을 입력하면 예외를 발생시킨다")
    void invalidCommandByClassName() {
        //given
        String invalidClassName = "invalid-command-className";

        //when&then
        Assertions.assertThrows(InvalidVoucherTypeException.class, () -> VoucherType.fromInputValue(invalidClassName));
    }
}