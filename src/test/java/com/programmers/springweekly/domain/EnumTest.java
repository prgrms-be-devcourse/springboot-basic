package com.programmers.springweekly.domain;

import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.domain.voucher.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnumTest {

    @ParameterizedTest
    @ValueSource(strings = {"show", "whitelist", "juice"})
    @DisplayName("프로그램 메뉴에 없는 메뉴가 입력되면 예외를 발생시킨다.")
    void programMenuTest(String input) {
        assertThatThrownBy(() -> ProgramMenu.findProgramMenu(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input: " + input + ", 찾으시는 프로그램 메뉴가 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"accVoucher", "divideVoucher"})
    @DisplayName("바우처 타입에 없는 타입이 입력되면 예외를 발생시킨다.")
    void voucherTypeTest(String input) {
        assertThatThrownBy(() -> VoucherType.findVoucherMenu(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input: " + input + ", 찾으시는 바우처 타입이 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"diamond", "silver", "bronze"})
    @DisplayName("고객 타입에 없는 타입이 입력되면 예외를 발생시킨다.")
    void customerTypeTest(String input) {
        assertThatThrownBy(() -> CustomerType.findCustomerType(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Input: " + input + ", 찾으시는 고객 타입이 없습니다.");
    }
}
