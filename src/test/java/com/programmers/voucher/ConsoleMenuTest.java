package com.programmers.voucher;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.ConsoleCommandType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleMenuTest {

    @InjectMocks
    private ConsoleMenu consoleMenu;

    @Mock
    private VoucherController voucherController;

    @Mock
    private CustomerController customerController;

    @Mock
    private Console console;

    @Test
    @DisplayName("성공: create 명령 입력")
    public void commandTypeCreate() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CREATE);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(voucherController).should().createVoucher();
    }

    @Test
    @DisplayName("성공: list 명령 입력")
    void commandTypeList() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.LIST);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(voucherController).should().findVouchers();
    }

    @Test
    @DisplayName("성공: blacklist 명령 입력")
    void commandTypeBlacklist() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.BLACKLIST);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(customerController).should().findBlacklistCustomers();
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

    @Test
    @DisplayName("성공: IllegalArgumentException 발생 - 예외 처리 후 재 입력")
    void run_ButThrownIllegalArgumentException_Then_KeepRunning() {
        //given
        doThrow(new IllegalArgumentException("Error message"))
                .when(voucherController).createVoucher();

        given(console.inputInitialCommand())
                .willReturn(ConsoleCommandType.CREATE, ConsoleCommandType.EXIT);

        //when
        consoleMenu.run();

        //then
        then(console).should().print("Error message");

        then(console).should(times(2)).inputInitialCommand();
        then(voucherController).should().createVoucher();
        then(console).should().exit();
    }

    @Test
    @DisplayName("성공: 예측하지 못한 예외 발생 - 예외 처리 후 종료")
    void run_ButThrownTheOtherExceptions_Then_ExitConsole() {
        //given
        doThrow(new RuntimeException("Error message"))
                .when(voucherController).createVoucher();

        given(console.inputInitialCommand())
                .willReturn(ConsoleCommandType.CREATE, ConsoleCommandType.EXIT);

        //when
        consoleMenu.run();

        //then
        then(console).should().print("Error message");

        then(console).should().inputInitialCommand();
        then(voucherController).should().createVoucher();
    }
}