package org.prgms.voucherProgram.console.menu;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class VoucherMenuTypeTest {

    @DisplayName("해당하는 메인메뉴를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"create,CREATE", "list,LIST", "exit,EXIT", "update,UPDATE", "delete,DELETE", "wallet,WALLET"})
    void fromMainMenu_MenuCommand_ReturnMenuType(String command, VoucherMenuType voucherMenuType) {
        assertThat(VoucherMenuType.fromMainMenu(command)).isEqualTo(voucherMenuType);
    }

    @DisplayName("해당하는 서브메뉴를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"exit,EXIT", "assign,ASSIGN", "list,LIST", "find,FIND", "delete,DELETE"})
    void fromSubMenu_MenuCommand_ReturnMenuType(String command, VoucherMenuType voucherMenuType) {
        assertThat(VoucherMenuType.fromSubMenu(command)).isEqualTo(voucherMenuType);
    }

    @DisplayName("올바르지 않은 메인메뉴 command은 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asd", "", "  ", "lists"})
    void fromMainMenu_WrongMenuCommand_ThrowsException(String command) {
        assertThatThrownBy(() -> VoucherMenuType.fromMainMenu(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 메뉴 입력이 아닙니다.");
    }

    @DisplayName("올바르지 않은 서브메뉴 command은 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asd", "", "  ", "update"})
    void fromSubMenu_WrongMenuCommand_ThrowsException(String command) {
        assertThatThrownBy(() -> VoucherMenuType.fromSubMenu(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 메뉴 입력이 아닙니다.");
    }
}
