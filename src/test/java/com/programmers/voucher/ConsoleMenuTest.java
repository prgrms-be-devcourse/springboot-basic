package com.programmers.voucher;

import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import com.programmers.voucher.global.io.menu.ConsoleCustomerMenu;
import com.programmers.voucher.global.io.menu.ConsoleMenu;
import com.programmers.voucher.global.io.menu.ConsoleVoucherMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ConsoleMenuTest {

    @InjectMocks
    private ConsoleMenu consoleMenu;

    @Mock
    private Console console;

    @Mock
    private ConsoleCustomerMenu consoleCustomerMenu;

    @Mock
    private ConsoleVoucherMenu consoleVoucherMenu;

    @Test
    @DisplayName("성공: voucher 명령 입력")
    void commandTypeVoucher() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.VOUCHER);

        //when
        consoleMenu.runClient();

        //then
        then(consoleVoucherMenu).should().runningVoucherService();
    }

    @Test
    @DisplayName("성공: customer 명령 입력")
    void commandTypeCustomer() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CUSTOMER);

        //when
        consoleMenu.runClient();

        //then
        then(consoleCustomerMenu).should().runningCustomerService();
    }

    @Test
    @DisplayName("성공: help 명령 입력")
    void commandTypeHelp() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.HELP);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should().printCommandSet();
    }

    @Test
    @DisplayName("성공: exit 명령 입력")
    void commandTypeExit() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should().exit();
    }
}