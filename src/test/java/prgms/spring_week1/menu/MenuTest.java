package prgms.spring_week1.menu;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import prgms.spring_week1.exception.NoSuchOptionValueException;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @ParameterizedTest
    @ValueSource(strings = {"EXIT", "exit", "creaTE", "List", "Black"})
    void findMenuType(String inputText) {
        assertDoesNotThrow(() -> Menu.findMenuType(inputText));
    }

    @ParameterizedTest
    @ValueSource(strings = {"EXI", "crrea", "EXwww"})
    void MenuType_fail(String inputText) {
        Throwable exception = assertThrows(NoSuchOptionValueException.class, () -> Menu.findMenuType(inputText));
        assertEquals("해당 메뉴 타입이 존재하지 않습니다.", exception.getMessage());
    }
}
