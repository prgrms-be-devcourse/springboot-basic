package org.programmers.springbootbasic.io;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.exception.WrongCommandInputException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class ConsoleTest {

    @Test
    @DisplayName("사용자가 exit 입력을 했을 때 EXIT Enum이 반환되어야 한다.")
    void 사용자_exit_입력_테스트() throws Exception {
        //given
        String input = "exit";
        assertThat(VoucherMainMenuCommand.EXIT, is(VoucherMainMenuCommand.valueOfCommand(input)));
    }

    @Test
    @DisplayName("사용자가 create 입력을 했을 때 CREATE Enum이 반환되어야 한다.")
    void 사용자_create_입력_테스트() throws Exception {
        //given
        String input = "create";
        assertThat(VoucherMainMenuCommand.CREATE, is(VoucherMainMenuCommand.valueOfCommand(input)));
    }

    @Test
    @DisplayName("사용자가 list 입력을 했을 때 LIST Enum이 반환되어야 한다.")
    void 사용자_list_입력_테스트() throws Exception {
        //given
        String input = "list";
        assertThat(VoucherMainMenuCommand.LIST, is(VoucherMainMenuCommand.valueOfCommand(input)));
    }

    @Test
    @DisplayName("사용자가 정해진 값 이외를 입력 했을 때 WrongCommandException이 발생해야 한다.")
    void 사용자_오류_입력_테스트() throws Exception {
        //given
        String wrongInput1 = "aas";

        // wrong input.
        Assertions.assertThatThrownBy(() -> {
            VoucherMainMenuCommand.valueOfCommand(wrongInput1);
        }).isInstanceOf(WrongCommandInputException.class);
    }
}