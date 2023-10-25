package com.programmers.vouchermanagement.consoleapp.menu;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.vouchermanagement.consoleapp.io.ConsoleManager;
import com.programmers.vouchermanagement.customer.controller.CustomerController;
import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
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
        doReturn(Menu.EXIT).when(consoleManager).selectMenu();

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(false));

        //verify
        verify(consoleManager).printExit();
    }

    @Test
    @DisplayName("잘못된 메뉴를 입력하면 해당 뷰를 출력하지만 시스템은 계속 된다.")
    void testHandleMenuTrue_IncorrectMenu() {
        //given
        doReturn(Menu.INCORRECT_MENU).when(consoleManager).selectMenu();

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(true));

        //verify
        verify(consoleManager).printIncorrectMenu();
    }

    @Test
    @DisplayName("바우처 생성 시 잘못된 타입을 입력하면 메뉴 실행을 실패하고, 시스템은 계속 된다.")
    void testHandleMenuFailed_IncorrectVoucherType() {
        //given
        final IllegalArgumentException exception = new IllegalArgumentException();
        doReturn(Menu.CREATE).when(consoleManager).selectMenu();
        doThrow(exception).when(consoleManager).instructCreate();

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(true));

        //verify
        verify(consoleManager).printException(exception);
    }

    @Test
    @DisplayName("바우처 생성 시 유효하지 않은 할인 값을 입력하면 메뉴 실행을 실패하고, 시스템은 계속 된다.")
    void testHandleMenuFailed_InvalidDiscountValue() {
        //given
        final IllegalArgumentException exception = new IllegalArgumentException();
        final CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal(0), VoucherType.FIXED);
        doReturn(Menu.CREATE).when(consoleManager).selectMenu();
        doReturn(request).when(consoleManager).instructCreate();
        doThrow(exception).when(voucherController).create(request);

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(true));

        //verify
        verify(consoleManager).printException(exception);
    }

    @Test
    @DisplayName("바우처 생성 메뉴 실행을 성공하고, 시스템은 계속 된다.")
    void testHandleMenuSuccessful_VoucherCreated() {
        //given
        final CreateVoucherRequest request = new CreateVoucherRequest(new BigDecimal(10000), VoucherType.FIXED);
        final Voucher voucher = new Voucher(UUID.randomUUID(), request.discountValue(), request.voucherType());
        final VoucherResponse response = VoucherResponse.from(voucher);
        doReturn(Menu.CREATE).when(consoleManager).selectMenu();
        doReturn(request).when(consoleManager).instructCreate();
        doReturn(response).when(voucherController).create(request);

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(true));

        //verify
        verify(consoleManager).printCreateResult(response);
    }

    @Test
    @DisplayName("바우처 전체 조회 시 저장된 바우처가 없어도 실패하지 않는다.")
    void testHandleMenuSuccessful_EmptyVoucherList() {
        //given
        final List<VoucherResponse> vouchers = Collections.emptyList();
        doReturn(Menu.LIST).when(consoleManager).selectMenu();
        doReturn(vouchers).when(voucherController).readAllVouchers();

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(true));

        //verify
        verify(consoleManager).printReadAllVouchers(vouchers);
    }

    @Test
    @DisplayName("저장된 바우처가 있을 때 바우처 전체 조회를 성공한다.")
    void testHandleMenuSuccessful_ReadAllVouchers() {
        //given
        final Voucher firstVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        final Voucher secondVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(40), VoucherType.PERCENT);
        final VoucherResponse firstResponse = VoucherResponse.from(firstVoucher);
        final VoucherResponse secondResponse = VoucherResponse.from(secondVoucher);
        final List<VoucherResponse> vouchers = List.of(firstResponse, secondResponse);
        doReturn(Menu.LIST).when(consoleManager).selectMenu();
        doReturn(vouchers).when(voucherController).readAllVouchers();

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(true));

        //verify
        verify(consoleManager).printReadAllVouchers(vouchers);
    }

    @Test
    @DisplayName("블랙리스트 조회 시 저장된 고객이 없어도 실패하지 않는다.")
    void testHandleMenuSuccessful_EmptyBlacklist() {
        //given
        final List<CustomerResponse> blacklist = Collections.emptyList();
        doReturn(Menu.BLACKLIST).when(consoleManager).selectMenu();
        doReturn(blacklist).when(customerController).readBlacklist();

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(true));

        //verify
        verify(consoleManager).printReadBlacklist(blacklist);
    }

    @Test
    @DisplayName("저장된 고객이 있을 때 블랙리스트 조회를 성공한다.")
    void testHandleMenuSuccessful_ReadBlacklist() {
        //given
        final Customer firstCustomer = new Customer(UUID.randomUUID(), "test-user1");
        final Customer secondCustomer = new Customer(UUID.randomUUID(), "test-user2");
        final CustomerResponse firstResponse = CustomerResponse.from(firstCustomer);
        final CustomerResponse secondResponse = CustomerResponse.from(secondCustomer);
        final List<CustomerResponse> blacklist = List.of(firstResponse, secondResponse);
        doReturn(Menu.BLACKLIST).when(consoleManager).selectMenu();
        doReturn(blacklist).when(customerController).readBlacklist();

        //when
        final boolean isRunning = menuHandler.handleMenu();

        //then
        assertThat(isRunning, is(true));

        //verify
        verify(consoleManager).printReadBlacklist(blacklist);
    }
}
