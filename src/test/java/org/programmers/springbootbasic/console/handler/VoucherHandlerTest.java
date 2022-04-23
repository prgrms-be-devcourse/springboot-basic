package org.programmers.springbootbasic.console.handler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.console.model.Model;
import org.programmers.springbootbasic.console.request.ConsoleRequest;
import org.programmers.springbootbasic.voucher.domain.Voucher;
import org.programmers.springbootbasic.voucher.service.VoucherService;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.INPUT_AND_REDIRECT;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.command.InputCommand.CREATE;
import static org.programmers.springbootbasic.console.command.InputCommand.LIST;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_AMOUNT;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_COMPLETE;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.FIXED;

class VoucherHandlerTest {

    private static final VoucherService voucherServiceMock = mock(VoucherService.class);
    private static final VoucherHandler voucherHandler = new VoucherHandler(voucherServiceMock);

    @BeforeAll
    static void initializeHandler() {
        voucherHandler.initCommandList();
    }

    @Test
    @DisplayName("명령어: CREATE")
    void handleCreateRequest() {
        //TODO: 테스트 가독성
        ConsoleRequest requestMock = mock(ConsoleRequest.class);
        when(requestMock.getCommand()).thenReturn(CREATE);
        var modelMock = mock(Model.class);
        when(requestMock.getModel()).thenReturn(modelMock);

        var modelAndView = voucherHandler.handleRequest(requestMock);
        verify(modelMock, times(1)).setRedirectLink(CREATE_AMOUNT);

        assertThat(modelAndView.getResponseCode(), is(INPUT_AND_REDIRECT));
    }

    @Test
    @DisplayName("명령어: CREATE_AMOUNT")
    void handleCreateAmountRequest() {
        ConsoleRequest requestMock = mock(ConsoleRequest.class);
        when(requestMock.getCommand()).thenReturn(CREATE_AMOUNT);
        var modelMock = mock(Model.class);
        when(requestMock.getModel()).thenReturn(modelMock);
        when(modelMock.getAttributes("type")).thenReturn("2");

        var modelAndView = voucherHandler.handleRequest(requestMock);

        verify(modelMock, times(1)).setRedirectLink(CREATE_COMPLETE);
        assertThat(modelAndView.getResponseCode(), is(INPUT_AND_REDIRECT));
    }

    @Test
    @DisplayName("명령어: CREATE_COMPLETE")
    void handleCreateCompleteRequest() {
        ConsoleRequest requestMock = mock(ConsoleRequest.class);
        when(requestMock.getCommand()).thenReturn(CREATE_COMPLETE);
        var modelMock = mock(Model.class);
        when(requestMock.getModel()).thenReturn(modelMock);
        when(modelMock.getAttributes("type")).thenReturn("1");
        when(modelMock.getAttributes("amount")).thenReturn("3000");

        var modelAndView = voucherHandler.handleRequest(requestMock);

        verify(voucherServiceMock, times(1)).registerVoucher(3000, FIXED);
        verify(modelMock, times(1)).clear();
        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }

    @Test
    @DisplayName("명령어: LIST - 저장된 바우처가 없을 때")
    void handleListWithNoVoucher() {
        ConsoleRequest requestMock = mock(ConsoleRequest.class);
        when(requestMock.getCommand()).thenReturn(LIST);
        var modelMock = mock(Model.class);
        when(requestMock.getModel()).thenReturn(modelMock);
        when(voucherServiceMock.getAllVouchers()).thenReturn(new ArrayList<>());

        var modelAndView = voucherHandler.handleRequest(requestMock);

        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }

    @Test
    @DisplayName("명령어: LIST - 저장된 바우처가 있을 때")
    void handleListWithSomeVoucher() {
        ConsoleRequest requestMock = mock(ConsoleRequest.class);
        when(requestMock.getCommand()).thenReturn(LIST);
        var modelMock = mock(Model.class);
        when(requestMock.getModel()).thenReturn(modelMock);

        ArrayList<Voucher> vouchers = new ArrayList<>();
        Voucher voucherMock1 = mock(Voucher.class);
        Voucher voucherMock2 = mock(Voucher.class);
        when(voucherServiceMock.getAllVouchers()).thenReturn(vouchers);

        var modelAndView = voucherHandler.handleRequest(requestMock);

        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }
}