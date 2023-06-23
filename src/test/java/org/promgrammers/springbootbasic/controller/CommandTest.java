package org.promgrammers.springbootbasic.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.promgrammers.springbootbasic.domain.voucher.model.Command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandTest {

    @Test
    @DisplayName("생성 성공 - 입력값 exit -> Command.EXIT")
    void createCommandExitTest() throws Exception {

        //given
        String inputCommand = "exit";

        //when
        Command createdCommand = assertDoesNotThrow(() -> Command.of(inputCommand));

        //then
        assertThat(createdCommand).isEqualTo(Command.EXIT);
    }

    @Test
    @DisplayName("생성 성공 - 입력값 exit -> Command.CREATE")
    void createCommandCREATETest() throws Exception {

        //given
        String inputCommand = "create";

        //when
        Command createdCommand = assertDoesNotThrow(() -> Command.of(inputCommand));

        //then
        assertThat(createdCommand).isEqualTo(Command.CREATE);
    }

    @Test
    @DisplayName("생성 성공 - 입력값 list -> Command.LIST")
    void createCommandListTest() throws Exception {

        //given
        String inputCommand = "list";

        //when
        Command createdCommand = assertDoesNotThrow(() -> Command.of(inputCommand));

        //then
        assertThat(createdCommand).isEqualTo(Command.LIST);
    }

    @Test
    @DisplayName("생성 성공 - 입력값 blackList -> Command.BLACKLIST")
    void createCommandBlackListTest() throws Exception {

        //given
        String inputCommand = "blackList";

        //when
        Command createdCommand = assertDoesNotThrow(() -> Command.of(inputCommand));

        //then
        assertThat(createdCommand).isEqualTo(Command.BLACKLIST);
    }

    @DisplayName("생성 실패 - 입력값이 커맨드 타입에 없는 값")
    @ParameterizedTest
    @ValueSource(strings = {"exi", "reate", "listttt"})
    void createCommandFailTest(String input) throws Exception {

        assertThrows(IllegalArgumentException.class, () -> Command.of(input));
    }
}