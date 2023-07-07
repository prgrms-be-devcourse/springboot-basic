package org.prgms.vouchermanagement.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

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
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);

        assertThatThrownBy(() -> CommandMenu.getCommandMenu(input2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);

        assertThatThrownBy(() -> CommandMenu.getCommandMenu(input3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ExceptionMessageConstant.COMMAND_INPUT_EXCEPTION);

    }

    @Test
    @DisplayName("메뉴 선택 확인")
    void getCommandMenu_Test() {
        //given
        String input1 = "create";
        String input2 = "list";
        String input3 = "exit";
        String input4 = "black";

        //when
        CommandMenu command1= CommandMenu.getCommandMenu(input1);
        CommandMenu command2= CommandMenu.getCommandMenu(input2);
        CommandMenu command3= CommandMenu.getCommandMenu(input3);
        CommandMenu command4= CommandMenu.getCommandMenu(input4);

        //then
        assertThat(command1, is(CommandMenu.CREATE_NEW_VOUCHER));
        assertThat(command2, is(CommandMenu.SHOW_VOUCHER_LIST));
        assertThat(command3, is(CommandMenu.EXIT));
        assertThat(command4, is(CommandMenu.SHOW_BLACK_LIST));

    }

    @Test
    @DisplayName("바우처 생성 메뉴 선택 확인 test")
    void getCommandMenu_Create_Test() {
        //given
        String input = "create";

        //when
        CommandMenu command = CommandMenu.getCommandMenu(input);

        //then
        assertThat(command, is(CommandMenu.CREATE_NEW_VOUCHER));
    }

    @Test
    @DisplayName("바우처 리스트 출력 메뉴 선택 확인 test")
    void getCommandMenu_List_Test() {
        //given
        String input = "list";

        //when
        CommandMenu command = CommandMenu.getCommandMenu(input);

        //then
        assertThat(command, is(CommandMenu.SHOW_VOUCHER_LIST));
    }

    @Test
    @DisplayName("종료 메뉴 선택 확인 test")
    void getCommandMenu_Exit_Test() {
        //given
        String input = "exit";

        //when
        CommandMenu command = CommandMenu.getCommandMenu(input);

        //then
        assertThat(command, is(CommandMenu.EXIT));
    }

    @Test
    @DisplayName("블랙 리스트 출력 메뉴 선택 확인 test")
    void getCommandMenu_Black_Test() {
        //given
        String input = "black";

        //when
        CommandMenu command = CommandMenu.getCommandMenu(input);

        //then
        assertThat(command, is(CommandMenu.SHOW_BLACK_LIST));
    }

    @Test
    @DisplayName("customer 리스트 출력 메뉴 선택 확인 test")
    void getCommandMenu_Customers_Test() {
        //given
        String input = "customers";

        //when
        CommandMenu command = CommandMenu.getCommandMenu(input);

        //then
        assertThat(command, is(CommandMenu.SHOW_CUSTOMER_LIST));
    }
}
