package com.programmers.vouchermanagement.consoleapp.menu;

import static com.programmers.vouchermanagement.consoleapp.menu.Menu.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.controller.CustomerController;
import com.programmers.vouchermanagement.voucher.controller.VoucherController;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;

class MenuHandlerTest {
    ConsoleManager consoleManager;
    MenuHandler menuHandler;
    VoucherController voucherController;
    CustomerController customerController;

    @BeforeEach
    void setUp() {
        consoleManager = mock(ConsoleManager.class);
        voucherController = mock(VoucherController.class);
        customerController = mock(CustomerController.class);
        menuHandler = new MenuHandler(consoleManager, voucherController, customerController);
    }

    @Test
    @DisplayName("exit을 입력할 시 해당 뷰를 출력하고 시스템을 종료한다.")
    void testHandleMenuFalse_Exit() {
        //given
        doReturn(EXIT).when(consoleManager).selectMenu();

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(false));

        //verify
        verify(consoleManager).printExit();
    }
}
