package prgms.spring_week1.menu;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import prgms.spring_week1.exception.NoSuchOptionValueException;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @ParameterizedTest
    @ValueSource(strings = {"EXIT", "exit", "creaTE","List","Black"})
    void findMenuType(String inputText) {
        assertDoesNotThrow(() -> Menu.findMenuType(inputText));
    }

    @ParameterizedTest
    @ValueSource(strings = {"EXI", "crrea", "EXwww"})
    void MenuType_fail(String inputText) {
        assertThrows(NoSuchOptionValueException.class, () -> Menu.findMenuType(inputText));
    }
}
