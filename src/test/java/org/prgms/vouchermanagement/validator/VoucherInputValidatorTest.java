package org.prgms.vouchermanagement.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.vouchermanagement.constant.ExceptionMessageConstant;

import java.util.InputMismatchException;

class VoucherInputValidatorTest {

    VoucherInputValidator validator = new VoucherInputValidator();

    @Test
    @DisplayName("Create Voucher 타입 선택 입력 Exception")
    void checkVoucherTypeInput_EXCEPTION_TEST() {
        //given
        String input1 = "01";
        String input2 = "5";
        String input3 = "";

        //when, then
        Assertions.assertThatThrownBy(() -> validator.checkVoucherTypeInput(input1))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);

        Assertions.assertThatThrownBy(() -> validator.checkVoucherTypeInput(input2))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);

        Assertions.assertThatThrownBy(() -> validator.checkVoucherTypeInput(input3))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);

    }

    @Test
    @DisplayName("Create Voucher 타입 선택 입력 테스트")
    void checkVoucherTypeInput_TEST() {
        //given
        String input1 = "1";
        String input2 = "2";

        //when, then
        Assertions.assertThatCode(() -> validator.checkVoucherTypeInput(input1))
                .doesNotThrowAnyException();

        Assertions.assertThatCode(() -> validator.checkVoucherTypeInput(input2))
                .doesNotThrowAnyException();

    }
}