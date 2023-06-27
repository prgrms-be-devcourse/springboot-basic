package com.programmers.voucher;

import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.ConsoleCommandType;
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
    private VoucherController voucherController;

    @Mock
    private Console console;

    @Test
    @DisplayName("create 명령 입력 - 성공")
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
    @DisplayName("list 명령 입력 - 성공")
    void commandTypeList() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.LIST);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(voucherController).should().findVouchers();
    }
}