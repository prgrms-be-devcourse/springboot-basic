package com.prgrms.management.command;

import com.prgrms.management.command.domain.Command;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;


class CommandTest {

    @Test
    void CREATEVOUCHER_Command_입력() {
        //given
        String inputCommand = "CREATEVOUCHER";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.CREATEVOUCHER);
    }
    @Test
    void LISTVOUCHER_Command_입력() {
        //given
        String inputCommand = "LISTVOUCHER";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.LISTVOUCHER);
    }
    @Test
    void BLACKLIST_Command_입력() {
        //given
        String inputCommand = "BLACKLIST";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.BLACKLIST);
    }
    @Test
    void CREATECUSTOMER_Command_입력() {
        //given
        String inputCommand = "CREATECUSTOMER";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.CREATECUSTOMER);
    }
    @Test
    void DELETECUSTOMER_Command_입력() {
        //given
        String inputCommand = "DELETECUSTOMER";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.DELETECUSTOMER);
    }
    @Test
    void DELETEALLCUSTOMER_Command_입력() {
        //given
        String inputCommand = "DELETEALLCUSTOMER";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.DELETEALLCUSTOMER);
    }
    @Test
    void FINDCUSTOMERBYID_Command_입력() {
        //given
        String inputCommand = "FINDCUSTOMERBYID";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.FINDCUSTOMERBYID);
    }
    @Test
    void FINDCUSTOMERBYEMAIL_Command_입력() {
        //given
        String inputCommand = "FINDCUSTOMERBYEMAIL";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.FINDCUSTOMERBYEMAIL);
    }
    @Test
    void LISTCUSTOMER_Command_입력() {
        //given
        String inputCommand = "LISTCUSTOMER";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.LISTCUSTOMER);
    }
    @Test
    void ASSIGNVOUCHER_Command_입력() {
        //given
        String inputCommand = "ASSIGNVOUCHER";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.ASSIGNVOUCHER);
    }
    @Test
    void DELETEVOUCHER_Command_입력() {
        //given
        String inputCommand = "DELETEVOUCHER";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.DELETEVOUCHER);
    }
    @Test
    void LISTVOUCHERWITHTYPE_Command_입력() {
        //given
        String inputCommand = "LISTVOUCHERWITHTYPE";
        //when
        Command commandThree = Command.of(inputCommand);
        //then
        Assertions.assertThat(commandThree).isEqualTo(Command.LISTVOUCHERWITHTYPE);
    }
    @Test
    void EXIT_Command_입력() {
        //given
        String inputCommand = "EXIT";
        //when
        Command command = Command.of(inputCommand);
        //then
        Assertions.assertThat(command).isEqualTo(Command.EXIT);
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