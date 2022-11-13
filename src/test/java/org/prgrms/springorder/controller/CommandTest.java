package org.prgrms.springorder.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class CommandTest {

    @DisplayName("생성 성공 테스트 - 입력값이 'exit'이면 Command.EXIT 가 생성된다. ")
    @Test
    void createCommandExitSuccessTest() {
        //given
        String inputString = "exit";
        //when
        Command createdCommand = assertDoesNotThrow(() -> Command.of(inputString));
        //then
        assertEquals(Command.EXIT, createdCommand);
    }

    @DisplayName("생성 성공 테스트 - 입력값이 'create'이면 Command.CREATE 가 생성된다. ")
    @Test
    void createCommandCREATESuccessTest() {
        //given
        String inputString = "create";
        //when
        Command createdCommand = assertDoesNotThrow(() -> Command.of(inputString));
        //then
        assertEquals(Command.CREATE, createdCommand);
    }

    @DisplayName("생성 성공 테스트 - 입력값이 'list'이면 Command.LIST 가 생성된다. ")
    @Test
    void createCommandListSuccessTest() {
        //given
        String inputString = "list";
        //when
        Command createdCommand = assertDoesNotThrow(() -> Command.of(inputString));
        //then
        assertEquals(Command.LIST, createdCommand);
    }

    @DisplayName("생성 성공 테스트 - 입력값이 'exit', 'create', 'list' 이면 예외를 던지지 않고 생성에 성공한다")
    @ParameterizedTest
    @ValueSource(strings = {"exit", "create", "list"})
    void createCommandSuccessTest(String inputString) {
        // given & when & then
        assertDoesNotThrow(()-> Command.of(inputString));
    }

    @DisplayName("생성 성공 실패 테스트 - 입력값이 'exit', 'create', 'list' 가 아니면 IllegalArgumentException 가 발생한다. ")
    @ParameterizedTest
    @ValueSource(strings = {"exis", "craete", "lsit", "llll", "ssss", "ddd", "omg"})
    void createCommandFailThrowsExceptionTest(String inputString) {
        // given & when & then
        assertThrows(IllegalArgumentException.class, ()-> Command.of(inputString));
    }

}