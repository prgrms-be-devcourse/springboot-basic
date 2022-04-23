package org.prgms.voucherProgram.console.menu;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerMenuTypeTest {

    @DisplayName("해당하는 메인 메뉴를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"create,CREATE", "read,READ", "update,UPDATE", "delete,DELETE", "exit,EXIT"})
    void from_MenuCommand_ReturnMenuType(String command, CustomerMenuType customerMenuType) {
        assertThat(CustomerMenuType.fromMainMenu(command)).isEqualTo(customerMenuType);
    }

    @DisplayName("해당하는 서브 메뉴를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"all,ALL", "one,JUST_ONE", "blacklist,BLACKLIST"})
    void fromSubMenu_MenuCommand_ReturnSubMenuType(String command, CustomerMenuType customerMenuType) {
        assertThat(CustomerMenuType.fromSubMenu(command)).isEqualTo(customerMenuType);
    }

    @DisplayName("올바르지 않은 메인 메뉴 command은 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asd", "", "  ", "lists"})
    void fromMainMenu_WrongMenuCommand_ThrowsException(String command) {
        assertThatThrownBy(() -> CustomerMenuType.fromMainMenu(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 메뉴 입력이 아닙니다.");
    }

    @DisplayName("올바르지 않은 서브 메뉴 command은 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asdsadf", "create", "  ", "read"})
    void fromSubMenu_WrongMenuCommand_ThrowsException(String command) {
        assertThatThrownBy(() -> CustomerMenuType.fromSubMenu(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 메뉴 입력이 아닙니다.");
    }
}
