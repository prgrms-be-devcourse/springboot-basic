package org.programmers.springorder.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.programmers.springorder.constant.VoucherMenuType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoucherMenuTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"1, CREATE", "2, LIST", "3, UPDATE", "4, DELETE", "5, BACK"})
    @DisplayName("유효한 바우처 메뉴 타입을 입력하면, 바우처 메뉴 타입을 반환한다.")
    void selectVoucherMenu(String menuNum, VoucherMenuType expectedVoucherMenuType) {
        // when
        VoucherMenuType result = VoucherMenuType.selectVoucherMenu(menuNum);

        // then
        assertThat(result).isEqualTo(expectedVoucherMenuType);
    }

    @ParameterizedTest
    @CsvSource(value = {"6", "hi"})
    @DisplayName("유효하지 않은 바우처 메뉴 타입을 입력하면, 에러 메시지를 띄운다.")
    void selectVoucherMenuFail(String menuNum) {
        // when
        assertThatThrownBy(() -> VoucherMenuType.selectVoucherMenu(menuNum))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 값입니다. 다시 입력해주세요.");
    }
}