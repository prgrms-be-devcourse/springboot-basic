package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.domain.voucher.controller.VoucherConsoleController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.VoucherCommandType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ConsoleVoucherMenuTest {

    @InjectMocks
    private ConsoleVoucherMenu consoleVoucherMenu;

    @Mock
    private Console console;

    @Mock
    private VoucherConsoleController voucherController;

    @Test
    @DisplayName("성공: create 명령 입력 - exit 명령 입력")
    public void voucherCommandTypeCreate() {
        //given
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.CREATE, VoucherCommandType.EXIT);

        //when
        consoleVoucherMenu.runningVoucherService();

        //then
        then(console).should(times(2)).inputVoucherCommandType();
        then(voucherController).should().createVoucher();
    }

    @Test
    @DisplayName("성공: list 명령 입력 - exit 명령 입력")
    void voucherCommandTypeList() {
        //given
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.LIST, VoucherCommandType.EXIT);

        //when
        consoleVoucherMenu.runningVoucherService();

        //then
        then(console).should(times(2)).inputVoucherCommandType();
        then(voucherController).should().findVouchers();
    }

    @Test
    @DisplayName("성공: voucher 명령 입력 - delete 명령 입력 - exit 명령 입력")
    void voucherCommandTypeDelete() {
        //given
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.DELETE, VoucherCommandType.EXIT);

        //when
        consoleVoucherMenu.runningVoucherService();

        //then
        then(console).should(times(2)).inputVoucherCommandType();
        then(voucherController).should().deleteVoucher();
    }

    @Test
    @DisplayName("성공: help 명령 입력 - exit 명령 입력")
    void voucherCommandTypeHelp() {
        //given
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.HELP, VoucherCommandType.EXIT);

        //when
        consoleVoucherMenu.runningVoucherService();

        //then
        then(console).should(times(2)).inputVoucherCommandType();
        then(console).should(times(2)).printVoucherCommandSet();
    }
}