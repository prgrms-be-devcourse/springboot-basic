package org.prgms.voucherProgram.console.menu;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ConsoleMenuTypeTest {

    @DisplayName("해당하는 메뉴를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"exit,EXIT", "customer,CUSTOMER", "voucher,VOUCHER"})
    void from_MenuCommand_ReturnMenuType(String command, ConsoleMenuType consoleMenuType) {
        assertThat(ConsoleMenuType.from(command)).isEqualTo(consoleMenuType);
    }

    @DisplayName("올바르지 않은 메뉴 command은 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asdsadf", "create", "  ", "read"})
    void from_WrongMenuCommand_ThrowsException(String command) {
        assertThatThrownBy(() -> ConsoleMenuType.from(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 메뉴 입력이 아닙니다.");
    }
}
