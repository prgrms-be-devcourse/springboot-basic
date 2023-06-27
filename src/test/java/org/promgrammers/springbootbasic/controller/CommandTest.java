package org.promgrammers.springbootbasic.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.promgrammers.springbootbasic.domain.voucher.model.Command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandTest {

    @ParameterizedTest
    @DisplayName("생성 성공 - 올바른 커맨합드 입력")
    @CsvSource(
            {"1, EXIT", "2, CREATE", "3,LIST", "4,BLACKLIST"})
    void createCommandSuccessTest(String inputCommand, Command expectedCommand) {

        //given -> when
        Command createdCommand = assertDoesNotThrow(() -> Command.of(inputCommand));

        //then
        assertThat(createdCommand).isEqualTo(expectedCommand);
    }

    @DisplayName("생성 실패 - 올바르지 않은 커맨드 입력")
    @ParameterizedTest
    @ValueSource(strings = {"exi", "reate", "listttt", "blackListtt", "5", "7", "&^^", "", " "})
    void createCommandFailTest(String input) throws Exception {

        assertThrows(IllegalArgumentException.class, () -> Command.of(input));
    }
}