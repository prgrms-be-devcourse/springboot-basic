package com.prgrms.management.command;

import com.prgrms.management.command.domain.Command;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;


class CommandTest {

    @Test
    void CREATE_Command_입력() {
        //given
        String inputCommand = "create";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.CREATEVOUCHER);
    }
    @Test
    void LIST_Command_입력() {
        //given
        String inputCommand = "list";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.LISTVOUCHER);
    }
    @Test
    void BLACKLIST_Command_입력() {
        //given
        String inputCommandThree = "blacklist";
        //when
        Command commandThree = Command.of(inputCommandThree);
        //then
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