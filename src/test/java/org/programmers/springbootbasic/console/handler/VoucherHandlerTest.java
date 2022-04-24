package org.programmers.springbootbasic.console.handler;

import org.junit.jupiter.api.AfterEach;
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
import static org.programmers.springbootbasic.console.command.InputCommand.CREATE_VOUCHER;
import static org.programmers.springbootbasic.console.command.InputCommand.LIST_VOUCHER;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_VOUCHER_AMOUNT;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_VOUCHER_COMPLETE;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.FIXED;

class VoucherHandlerTest {

    private static final VoucherService VOUCHER_SERVICE_MOCK = mock(VoucherService.class);
    private static final ConsoleRequest CONSOLE_REQUEST_MOCK = mock(ConsoleRequest.class);
    private static final Model MODEL_MOCK = mock(Model.class);

    private static final VoucherHandler VOUCHER_HANDLER = new VoucherHandler(VOUCHER_SERVICE_MOCK);

    @AfterEach
    void resetMock() {
        reset(VOUCHER_SERVICE_MOCK, CONSOLE_REQUEST_MOCK, MODEL_MOCK);
    }

    @BeforeAll
    static void initializeHandler() {
        VOUCHER_HANDLER.initCommandList();
    }

    @Test
    @DisplayName("명령어: CREATE")
    void handleCreateRequest() {
        when(CONSOLE_REQUEST_MOCK.getCommand()).thenReturn(CREATE_VOUCHER);
        when(CONSOLE_REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);

        var modelAndView = VOUCHER_HANDLER.handleRequest(CONSOLE_REQUEST_MOCK);
        verify(MODEL_MOCK, times(1)).setRedirectLink(CREATE_VOUCHER_AMOUNT);

        assertThat(modelAndView.getResponseCode(), is(INPUT_AND_REDIRECT));
    }

    @Test
    @DisplayName("명령어: CREATE_AMOUNT")
    void handleCreateAmountRequest() {
        when(CONSOLE_REQUEST_MOCK.getCommand()).thenReturn(CREATE_VOUCHER_AMOUNT);
        when(CONSOLE_REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);
        when(MODEL_MOCK.getAttributes("type")).thenReturn("2");

        var modelAndView = VOUCHER_HANDLER.handleRequest(CONSOLE_REQUEST_MOCK);

        verify(MODEL_MOCK, times(1)).setRedirectLink(CREATE_VOUCHER_COMPLETE);
        assertThat(modelAndView.getResponseCode(), is(INPUT_AND_REDIRECT));
    }

    @Test
    @DisplayName("명령어: CREATE_COMPLETE")
    void handleCreateCompleteRequest() {
        when(CONSOLE_REQUEST_MOCK.getCommand()).thenReturn(CREATE_VOUCHER_COMPLETE);
        when(CONSOLE_REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);
        when(MODEL_MOCK.getAttributes("type")).thenReturn("1");
        when(MODEL_MOCK.getAttributes("amount")).thenReturn("3000");

        var modelAndView = VOUCHER_HANDLER.handleRequest(CONSOLE_REQUEST_MOCK);

        verify(VOUCHER_SERVICE_MOCK, times(1)).createVoucher(3000, FIXED);
        verify(MODEL_MOCK, times(1)).clear();
        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }

    @Test
    @DisplayName("명령어: LIST - 저장된 바우처가 없을 때")
    void handleListWithNoVoucher() {
        when(CONSOLE_REQUEST_MOCK.getCommand()).thenReturn(LIST_VOUCHER);
        when(CONSOLE_REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);
        when(VOUCHER_SERVICE_MOCK.getAllVouchers()).thenReturn(new ArrayList<>());

        var modelAndView = VOUCHER_HANDLER.handleRequest(CONSOLE_REQUEST_MOCK);

        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }

    @Test
    @DisplayName("명령어: LIST - 저장된 바우처가 있을 때")
    void handleListWithSomeVoucher() {
        when(CONSOLE_REQUEST_MOCK.getCommand()).thenReturn(LIST_VOUCHER);
        when(CONSOLE_REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);

        ArrayList<Voucher> vouchers = new ArrayList<>();
        vouchers.add(mock(Voucher.class));
        vouchers.add(mock(Voucher.class));

        when(VOUCHER_SERVICE_MOCK.getAllVouchers()).thenReturn(vouchers);

        var modelAndView = VOUCHER_HANDLER.handleRequest(CONSOLE_REQUEST_MOCK);

        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }
}