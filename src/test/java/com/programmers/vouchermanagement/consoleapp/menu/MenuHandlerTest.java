package com.programmers.vouchermanagement.consoleapp.menu;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.controller.CustomerController;
import com.programmers.vouchermanagement.voucher.controller.VoucherController;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;

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
        //when
        menuHandler.handleMenu(Menu.EXIT);

        //verify
        verify(consoleManager).printExit();
    }

    @Test
    @DisplayName("잘못된 메뉴를 입력하면 해당 뷰를 출력하지만 시스템은 계속 된다.")
    void testHandleMenuTrue_IncorrectMenu() {
        //when
        menuHandler.handleMenu(Menu.INCORRECT_MENU);

        //verify
        verify(consoleManager).printIncorrectMenu();
    }

    @Test
    @DisplayName("바우처 생성 시 잘못된 타입을 입력하면 메뉴 실행을 실패한다.")
    void testHandleMenuFailed_IncorrectVoucherType() {
        //given
        final IllegalArgumentException exception = new IllegalArgumentException();
        doReturn(VoucherMenu.CREATE).when(consoleManager).selectVoucherMenu();
        doThrow(exception).when(consoleManager).instructCreateVoucher();

        //when
        menuHandler.handleMenu(Menu.VOUCHER);

        //verify
        verify(consoleManager).printException(exception);
    }

    @Test
    @DisplayName("바우처 생성 시 유효하지 않은 할인 값을 입력하면 메뉴 실행을 실패한다.")
    void testHandleMenuFailed_InvalidDiscountValue() {
        //given
        final IllegalArgumentException exception = new IllegalArgumentException();
        final CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal(0), VoucherType.FIXED);
        doReturn(VoucherMenu.CREATE).when(consoleManager).selectVoucherMenu();
        doReturn(request).when(consoleManager).instructCreateVoucher();
        doThrow(exception).when(voucherController).create(request);

        //when
        menuHandler.handleMenu(Menu.VOUCHER);

        //verify
        verify(consoleManager).printException(exception);
    }

    @Test
    @DisplayName("바우처 생성 메뉴 실행을 성공한다.")
    void testHandleMenuSuccessful_VoucherCreated() {
        //given
        final CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal(10000), VoucherType.FIXED);
        doReturn(VoucherMenu.CREATE).when(consoleManager).selectVoucherMenu();
        doReturn(request).when(consoleManager).instructCreateVoucher();

        //when
        menuHandler.handleMenu(Menu.VOUCHER);

        //verify
        verify(voucherController).create(request);
    }

    @Test
    @DisplayName("바우처 전체 조회를 성공한다.")
    void testHandleMenuSuccessful_ReadAllVouchers() {
        //given
        doReturn(VoucherMenu.LIST).when(consoleManager).selectVoucherMenu();

        //when
        menuHandler.handleMenu(Menu.VOUCHER);

        //verify
        verify(voucherController).readAllVouchers();
    }

    @Test
    @DisplayName("블랙리스트 조회를 성공한다.")
    void testHandleMenuSuccessful_ReadBlacklist() {
        //given
        doReturn(CustomerMenu.BLACKLIST).when(consoleManager).selectCustomerMenu();

        //when
        menuHandler.handleMenu(Menu.CUSTOMER);

        //verify
        verify(customerController).readBlacklist();
    }
}
