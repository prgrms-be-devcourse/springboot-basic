package org.programmers.springbootbasic.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.data.VoucherMainMenuCommand;

class ConsoleTest {

    @Test
    public void 사용자_입력_테스트() throws Exception {
        //given
        String input1 = "exit";
        String input2 = "create";
        String input3 = "list";
        String wrongInput1 = "aas";
        String wrongInput2 = "123123";
        String wrongInput3 = "!#!#@#12312#@!!#@3";
        String wrongInput4 = "EXIT";

        // when
        //then

        Assertions.assertThat(VoucherMainMenuCommand.EXIT).isEqualTo(VoucherMainMenuCommand.valueOfCommand(input1));
        Assertions.assertThat(VoucherMainMenuCommand.CREATE).isEqualTo(VoucherMainMenuCommand.valueOfCommand(input2));
        Assertions.assertThat(VoucherMainMenuCommand.LIST).isEqualTo(VoucherMainMenuCommand.valueOfCommand(input3));

        // wrong input.
        Assertions.assertThat(VoucherMainMenuCommand.WRONG_INPUT).isEqualTo(VoucherMainMenuCommand.valueOfCommand(wrongInput1));
        Assertions.assertThat(VoucherMainMenuCommand.WRONG_INPUT).isEqualTo(VoucherMainMenuCommand.valueOfCommand(wrongInput2));
        Assertions.assertThat(VoucherMainMenuCommand.WRONG_INPUT).isEqualTo(VoucherMainMenuCommand.valueOfCommand(wrongInput3));
        Assertions.assertThat(VoucherMainMenuCommand.WRONG_INPUT).isEqualTo(VoucherMainMenuCommand.valueOfCommand(wrongInput4));
    }
}