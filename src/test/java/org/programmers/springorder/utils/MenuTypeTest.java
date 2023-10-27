package org.programmers.springorder.utils;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"1, EXIT", "2, VOUCHER", "3, CUSTOMER", "4, WALLET"})
    @DisplayName("유효한 메뉴 타입을 입력하면, 메뉴 타입을 반환한다.")
    void selectMenu(String menuNum, MenuType expectedVoucherType) {
        // when
        MenuType result = MenuType.selectMenu(menuNum);

        // then
        assertThat(result).isEqualTo(expectedVoucherType);
    }

    @ParameterizedTest
    @CsvSource(value = {"5", "hi"})
    @DisplayName("유효하지 않은 메뉴 타입을 입력하면, 에러 메시지를 띄운다.")
    void selectMenu(String menuNum) {
        assertThatThrownBy(() -> MenuType.selectMenu(menuNum))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 값입니다. 다시 입력해주세요.");
    }
}