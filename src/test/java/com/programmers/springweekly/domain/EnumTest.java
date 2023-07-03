package com.programmers.springweekly.domain;

import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.domain.voucher.VoucherType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EnumTest {

    @ParameterizedTest
    @ValueSource(strings = {"show", "whitelist", "voucher"})
    @DisplayName("프로그램 메뉴에 없는 메뉴가 입력되면 예외를 발생시킨다.")
    void programMenuTest(String input) {
        Assertions.assertThatThrownBy(() -> ProgramMenu.findProgramMenu(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input: " + input + ", The type you are looking for is not found.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"accVoucher", "divideVoucher"})
    @DisplayName("바우처 타입에 없는 타입이 입력되면 예외를 발생시킨다.")
    void voucherTypeTest(String input) {
        Assertions.assertThatThrownBy(() -> VoucherType.findVoucherMenu(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input: " + input + ", The type you are looking for is not found.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"diamond", "silver", "bronze"})
    @DisplayName("고객 타입에 없는 타입이 입력되면 예외를 발생시킨다.")
    void customerTypeTest(String input) {
        Assertions.assertThatThrownBy(() -> CustomerType.findCustomerType(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input: " + input + ", The type you are looking for is not found.");
    }

}
