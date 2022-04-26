package com.prgrms.management.command;

import com.prgrms.management.command.domain.Command;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;


class CommandTest {

    @Test
    void 성공_Command_입력() {
        //given
        String inputCommand = "1";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.CREATE_VOUCHER);
    }

    @Test
    void 잘못된_Command_입력() {
        //given
        String inputCommand = "15";
        //then
        Assertions.assertThatThrownBy(() -> Command.of(inputCommand))
                .isInstanceOf(NoSuchElementException.class);
    }
}