package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.domain.voucher.controller.VoucherConsoleController;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.VoucherCommandType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucherDto;
import static com.programmers.voucher.testutil.VoucherTestUtil.createPercentVoucherDto;
import static org.mockito.ArgumentMatchers.any;
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
    @DisplayName("성공: create 명령 실행 - exit 명령 실행")
    public void voucherCommandTypeCreate() {
        //given
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.CREATE, VoucherCommandType.EXIT);
        given(voucherController.createVoucher(any())).willReturn(UUID.randomUUID());

        //when
        consoleVoucherMenu.runningVoucherService();

        //then
        then(console).should().inputVoucherCreateInfo();
        then(voucherController).should().createVoucher(any());
        then(console).should().print(any());
    }

    @Test
    @DisplayName("성공: list 명령 실행 - exit 명령 실행")
    void voucherCommandTypeList() {
        //given
        VoucherDto fixedVoucher = createFixedVoucherDto(UUID.randomUUID(), 10);
        VoucherDto percentVoucher = createPercentVoucherDto(UUID.randomUUID(), 10);
        List<VoucherDto> vouchers = List.of(fixedVoucher, percentVoucher);

        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.LIST, VoucherCommandType.EXIT);
        given(voucherController.findVouchers()).willReturn(vouchers);

        //when
        consoleVoucherMenu.runningVoucherService();

        //then
        then(voucherController).should().findVouchers();
        then(console).should().printVouchers(any());
    }

    @Test
    @DisplayName("성공: delete 명령 실행 - exit 명령 실행")
    void voucherCommandTypeDelete() {
        //given
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.DELETE, VoucherCommandType.EXIT);

        //when
        consoleVoucherMenu.runningVoucherService();

        //then
        then(console).should().inputUUID();
        then(voucherController).should().deleteVoucher(any());
        then(console).should().print(any());
    }

    @Test
    @DisplayName("성공: help 명령 실행 - exit 명령 실행")
    void voucherCommandTypeHelp() {
        //given
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.HELP, VoucherCommandType.EXIT);

        //when
        consoleVoucherMenu.runningVoucherService();

        //then
        then(console).should(times(2)).printVoucherCommandSet();
    }
}