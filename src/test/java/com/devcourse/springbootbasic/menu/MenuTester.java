package com.devcourse.springbootbasic.menu;

import com.devcourse.springbootbasic.engine.exception.InvalidDataException;
import com.devcourse.springbootbasic.engine.io.InputConsole;
import com.devcourse.springbootbasic.engine.model.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;

public class MenuTester {
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "0"})
    public void menuTest(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputConsole inputConsole = new InputConsole();
        Menu menu = inputConsole.inputMenu();
        Assertions.assertNotNull(menu);
    }

    @ParameterizedTest
    @ValueSource(strings = {"13", "14", "가나다", "absd", "a", "ㄱ", "?"})
    public void menuExceptionTest(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputConsole inputConsole = new InputConsole();
        Assertions.assertThrows(InvalidDataException.class, inputConsole::inputMenu);
    }
}
