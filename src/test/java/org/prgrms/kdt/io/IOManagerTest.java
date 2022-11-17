package org.prgrms.kdt.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.exceptions.AmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.*;

class IOManagerTest {

    private Console console;
    private IOManager ioManager;

    private static final Logger logger = LoggerFactory.getLogger(IOManagerTest.class);


    private void createIOManager(String input) {
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        console = new Console();
        ioManager = new IOManager(console);
    }

    @DisplayName("할인 정도로는 정수 혹은 실수를 입력받는다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "0.1", "30", "100", "-100", "1234f", "5678d"})
    void getAmountInputTest(String input) {
        // given
        createIOManager(input);
        //when
        double amount = ioManager.getAmountInput();
        logger.info(MessageFormat.format("amount -> {0}", amount));
        //then
        assertEquals(Double.parseDouble(input), amount);
    }

    @DisplayName("할인 정도는 숫자만을 받을 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "1234p", "asdf", "five"})
    void getInvalidAmountInputTest(String input) {
        createIOManager(input);
        assertThrows(AmountException.class, () -> {
            ioManager.getAmountInput();
        });
        logger.info(MessageFormat.format("input -> {0}", input));
    }
}