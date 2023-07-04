package prgms.spring_week1.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import prgms.spring_week1.exception.NoSuchOptionValueException;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @ParameterizedTest
    @ValueSource(strings = {"EXIT", "exit", "creaTE", "List", "Black"})
    @DisplayName("올바른 메뉴를 입력했을 때 예외를 던지지 않는 지 확인")
    void findMenuType(String inputText) {
        assertDoesNotThrow(() -> Menu.findMenuType(inputText));
    }

    @ParameterizedTest
    @ValueSource(strings = {"EXI", "crrea", "EXwww"})
    @DisplayName("존재하지 않는 메뉴를 입력했을 때 예외를 던지는 지 확인")
    void findMenuType_fail(String inputText) {
        Throwable exception = assertThrows(NoSuchOptionValueException.class, () -> Menu.findMenuType(inputText));
        assertEquals("해당 메뉴 타입이 존재하지 않습니다.", exception.getMessage());
    }
}
