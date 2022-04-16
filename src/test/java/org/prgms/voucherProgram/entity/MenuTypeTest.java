package org.prgms.voucherProgram.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTypeTest {
    @DisplayName("해당하는 MenuType을 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"create,CREATE", "list,LIST", "exit,EXIT"})
    void of_MenuCommand_ReturnMenuType(String command, MenuType menuType) {
        assertThat(MenuType.of(command)).isEqualTo(menuType);
    }

    @DisplayName("올바르지 않은 command은 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asd", "", "  ", "lists"})
    void of_WrongMenuCommand_ThrowsException(String command) {
        assertThatThrownBy(() -> MenuType.of(command))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 올바른 메뉴 입력이 아닙니다.");
    }
}
