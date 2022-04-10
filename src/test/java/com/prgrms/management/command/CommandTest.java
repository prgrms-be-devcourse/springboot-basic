package com.prgrms.management.command;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;


class CommandTest {

    @Test
    void Command_입력() {
        //given
        String inputCommand = "create";
        String inputCommandTwo = "list";
        String inputCommandThree = "blacklist";
        //when
        Command command = Command.of(inputCommand);
        Command commandTwo = Command.of(inputCommandTwo);
        Command commandThree = Command.of(inputCommandThree);
        //then
        Assertions.assertThat(command).isEqualTo(Command.CREATE);
        Assertions.assertThat(commandTwo).isEqualTo(Command.LIST);
        Assertions.assertThat(commandThree).isEqualTo(Command.BLACKLIST);
    }

    @Test
    void 잘못된_Command_입력() {
        //given
        String inputCommand = "find";
        //then
        Assertions.assertThatThrownBy(() -> Command.of(inputCommand))
                .isInstanceOf(NoSuchElementException.class);
    }
}