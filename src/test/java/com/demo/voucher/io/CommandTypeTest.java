package com.demo.voucher.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CommandTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"exit", "create", "list"})
    @DisplayName("입력 받는 명령어 메뉴가 올바른 경우 테스트")
    void isValidCommandInput_메서드_올바른메뉴_입력테스트(String commandInput) {
        Assertions.assertTrue(CommandType.isValidCommandInput(commandInput));
    }

    @ParameterizedTest
    @ValueSource(strings = {"exitt", "0", "voucher", "2"})
    @DisplayName("입력 받는 명령어 메뉴가 올바르지 않은 경우 테스트")
    void isValidCommandInput_메서드_올바르지_않은_메뉴_입력테스트(String commandInput) {
        Assertions.assertFalse(CommandType.isValidCommandInput(commandInput));
    }
}