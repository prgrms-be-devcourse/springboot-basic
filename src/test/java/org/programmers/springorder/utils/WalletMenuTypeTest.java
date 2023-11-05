package org.programmers.springorder.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.programmers.springorder.constant.WalletMenuType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WalletMenuTypeTest {

    @ParameterizedTest
    @CsvSource(value = {"1, ASSIGN", "2, VOUCHER", "3, DELETE", "4, CUSTOMER", "5, BACK"})
    @DisplayName("유효한 지갑 메뉴 타입을 입력하면, 지갑 메뉴 타입을 반환한다.")
    void selectCustomerMenu(String menuNum, WalletMenuType expectedWalletMenuType) {
        // when
        WalletMenuType result = WalletMenuType.selectWalletMenu(menuNum);

        // then
        assertThat(result).isEqualTo(expectedWalletMenuType);
    }

    @ParameterizedTest
    @CsvSource(value = {"6", "hi"})
    @DisplayName("유효하지 않은 지갑 메뉴 타입을 입력하면, 에러 메시지를 띄운다.")
    void selectCustomerMenuFail(String menuNum) {
        // when
        assertThatThrownBy(() -> WalletMenuType.selectWalletMenu(menuNum))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 값입니다. 다시 입력해주세요.");
    }
}
