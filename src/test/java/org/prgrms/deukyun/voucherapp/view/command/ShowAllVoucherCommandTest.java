package org.prgrms.deukyun.voucherapp.view.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.view.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ShowAllVoucherCommandTest {

    ShowAllVoucherCommand command;
    VoucherService voucherService;
    ConsoleService console;

    @BeforeEach
    void setup() {
        voucherService = mock(VoucherService.class);
        console = mock(ConsoleService.class);
    }

    @Test
    void 성공_전체조회명령() {
        //given
        command = new ShowAllVoucherCommand(voucherService, console);

        //when
        command.showAllVoucher();

        //verify
        verify(voucherService).findAll();
        verify(console).write(any());
    }
}