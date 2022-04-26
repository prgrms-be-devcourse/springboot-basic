package org.prgrms.deukyun.voucherapp.app.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.app.console.ConsoleService;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ShowAllVoucherCommandTest {

    ShowAllVoucherCommand command;
    VoucherService mockVoucherService;
    ConsoleService mockConsole;

    @BeforeEach
    void setup() {
        mockVoucherService = mock(VoucherService.class);
        mockConsole = mock(ConsoleService.class);
    }

    @Test
    void 성공_전체조회명령() {
        //setup
        command = new ShowAllVoucherCommand(mockVoucherService, mockConsole);

        //action
        command.showAllVoucher();

        //verify
        verify(mockVoucherService).findAll();
        verify(mockConsole).write(any());
    }
}