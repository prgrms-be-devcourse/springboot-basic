package org.programmers.springorder.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerMenuTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"1, CREATE", "2, BLACK", "3, BACK"})
    @DisplayName("유효한 고객 메뉴 타입을 입력하면, 고객 메뉴 타입을 반환한다.")
    void selectCustomerMenu(String menuNum, CustomerMenuType expectedCustomerMenuType) {
        // when
        CustomerMenuType result = CustomerMenuType.selectCustomerMenu(menuNum);

        // then
        assertThat(result).isEqualTo(expectedCustomerMenuType);
    }

    @ParameterizedTest
    @CsvSource(value = {"4", "hi"})
    @DisplayName("유효하지 않은 고객 메뉴 타입을 입력하면, 에러 메시지를 띄운다.")
    void selectCustomerMenuFail(String menuNum) {
        // when
        assertThatThrownBy(() -> CustomerMenuType.selectCustomerMenu(menuNum))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 값입니다. 다시 입력해주세요.");
    }
}