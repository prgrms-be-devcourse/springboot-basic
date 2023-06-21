package org.prgms.vouchermanagement.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.vouchermanagement.constant.ExceptionMessageConstant;

import java.util.InputMismatchException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandMenuTest {

    @Test
    @DisplayName("잘못된 메뉴 선택 Exception 확인")
    void getCommandMenu_input_Exception_Test() {
        //given
        String input1 = "abc";
        String input2 = "";
        String input3 = "123";

        //when, then
        assertThatThrownBy(() -> CommandMenu.getCommandMenu(input1))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);

        assertThatThrownBy(() -> CommandMenu.getCommandMenu(input2))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);

        assertThatThrownBy(() -> CommandMenu.getCommandMenu(input3))
                .isInstanceOf(InputMismatchException.class)
                .hasMessageContaining(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);

    }

    @Test
    @DisplayName("메뉴 선택 확인")
    void getCommandMenu_Test() {
        //given
        String input1 = "create";
        String input2 = "list";
        String input3 = "exit";

        //when
        CommandMenu command1= CommandMenu.getCommandMenu(input1);
        CommandMenu command2= CommandMenu.getCommandMenu(input2);
        CommandMenu command3= CommandMenu.getCommandMenu(input3);

        //then
        assertThat(command1).isEqualTo(CommandMenu.CREATE_NEW_VOUCHER);
        assertThat(command2).isEqualTo(CommandMenu.SHOW_VOUCHER_LIST);
        assertThat(command3).isEqualTo(CommandMenu.EXIT);
    }
}