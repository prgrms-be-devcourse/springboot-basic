package org.prgms.vouchermanagement.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.vouchermanagement.constant.ExceptionMessageConstant;

import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThatThrownBy(() -> validator.checkVoucherTypeInput(input1))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);

        assertThatThrownBy(() -> validator.checkVoucherTypeInput(input2))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.VOUCHER_TYPE_INPUT_EXCEPTION);

        assertThatThrownBy(() -> validator.checkVoucherTypeInput(input3))
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
        assertThatCode(() -> validator.checkVoucherTypeInput(input1))
                .doesNotThrowAnyException();

        assertThatCode(() -> validator.checkVoucherTypeInput(input2))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("FixedAmountVoucher Amount 입력 Exception")
    void checkFixedAmount_Exception_Test() {
        //given
        String input1 = "";
        String input2 = "-30";
        String input3 = "abc";

        //when, then
        assertThatThrownBy(() -> validator.checkFixedAmount(input1))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.FIXED_VOUCHER_AMOUNT_INPUT_EXCEPTION);

        assertThatThrownBy(() -> validator.checkFixedAmount(input2))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.FIXED_VOUCHER_AMOUNT_INPUT_EXCEPTION);

        assertThatThrownBy(() -> validator.checkFixedAmount(input3))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.FIXED_VOUCHER_AMOUNT_INPUT_EXCEPTION);

    }

    @Test
    @DisplayName("FixedAmountVoucher Amount 입력 Test")
    void checkFixedAmount_Test() {
        //given
        String input1 = "1000";
        String input2 = "5000";
        String input3 = "300";

        //when, then
        assertThatCode(() -> validator.checkFixedAmount(input1))
                .doesNotThrowAnyException();

        assertThatCode(() -> validator.checkFixedAmount(input2))
                .doesNotThrowAnyException();

        assertThatCode(() -> validator.checkFixedAmount(input3))
                .doesNotThrowAnyException();


    }

    @Test
    @DisplayName("PercentDiscountVoucher Percent 입력 Exception")
    void checkPercent_Exception_Test() {
        //given
        String input1 = "";
        String input2 = "-30";
        String input3 = "abc";
        String input4 = "150";

        //when, then
        assertThatThrownBy(() -> validator.checkPercent(input1))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.PERCENT_DISCOUNT_INPUT_EXCEPTION);

        assertThatThrownBy(() -> validator.checkPercent(input2))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.PERCENT_DISCOUNT_INPUT_EXCEPTION);

        assertThatThrownBy(() -> validator.checkPercent(input3))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.PERCENT_DISCOUNT_INPUT_EXCEPTION);

        assertThatThrownBy(() -> validator.checkPercent(input4))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.PERCENT_DISCOUNT_INPUT_EXCEPTION);

    }

    @Test
    @DisplayName("PercentDiscountVoucher Percent 입력 Test")
    void checkPercent_Test() {
        //given
        String input1 = "1";
        String input2 = "99";
        String input3 = "49";
        String input4 = "30";

        //when, then
        assertThatCode(() -> validator.checkPercent(input1))
                .doesNotThrowAnyException();

        assertThatCode(() -> validator.checkPercent(input2))
                .doesNotThrowAnyException();

        assertThatCode(() -> validator.checkPercent(input3))
                .doesNotThrowAnyException();

        assertThatCode(() -> validator.checkPercent(input4))
                .doesNotThrowAnyException();

    }
}