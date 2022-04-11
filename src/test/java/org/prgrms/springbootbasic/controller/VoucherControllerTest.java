package org.prgrms.springbootbasic.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.VoucherType;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.view.ConsoleView;

class VoucherControllerTest {

    @DisplayName("EXIT 행위 테스트")
    @Test
    void exit() {
        //given
        var consoleViewMock = mock(ConsoleView.class);
        var voucherServiceMock = mock(VoucherService.class);
        when(consoleViewMock.inputMenu()).thenReturn(Menu.EXIT);

        var voucherController = new VoucherController(voucherServiceMock, consoleViewMock);

        //when
        boolean actual = voucherController.process();

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("LIST 테스트")
    @Test
    void list() {
        //given
        var consoleViewMock = mock(ConsoleView.class);
        var voucherServiceMock = mock(VoucherService.class);
        when(consoleViewMock.inputMenu()).thenReturn(Menu.LIST);
        List<Voucher> vouchers = List.of(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        when(voucherServiceMock.findAll()).thenReturn(vouchers);

        var voucherController = new VoucherController(voucherServiceMock, consoleViewMock);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleViewMock, voucherServiceMock);
        inOrder.verify(consoleViewMock).inputMenu();
        inOrder.verify(voucherServiceMock).findAll();
        inOrder.verify(consoleViewMock).printList(vouchers);
    }

    @DisplayName("FixedAmountVoucher 생성 테스트")
    @Test
    void createFixedAmountVoucher() {
        //given
        var consoleViewMock = mock(ConsoleView.class);
        var voucherServiceMock = mock(VoucherService.class);
        when(consoleViewMock.inputMenu()).thenReturn(Menu.CREATE);
        when(consoleViewMock.selectVoucherType()).thenReturn(VoucherType.FIXED);
        long amount = 10L;
        when(consoleViewMock.selectAmount()).thenReturn(amount);

        var voucherController = new VoucherController(voucherServiceMock, consoleViewMock);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleViewMock, voucherServiceMock);
        inOrder.verify(consoleViewMock).inputMenu();
        inOrder.verify(consoleViewMock).selectVoucherType();
        inOrder.verify(consoleViewMock).selectAmount();
        inOrder.verify(voucherServiceMock).createFixedAmountVoucher(amount);
    }

    @DisplayName("PercentAmountVoucher 생성 테스트")
    @Test
    void createPercentAmountVoucher() {
        //given
        var consoleViewMock = mock(ConsoleView.class);
        var voucherServiceMock = mock(VoucherService.class);
        when(consoleViewMock.inputMenu()).thenReturn(Menu.CREATE);
        when(consoleViewMock.selectVoucherType()).thenReturn(VoucherType.PERCENT);
        int percent = 10;
        when(consoleViewMock.selectPercent()).thenReturn(percent);

        var voucherController = new VoucherController(voucherServiceMock, consoleViewMock);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleViewMock, voucherServiceMock);
        inOrder.verify(consoleViewMock).inputMenu();
        inOrder.verify(consoleViewMock).selectVoucherType();
        inOrder.verify(consoleViewMock).selectPercent();
        inOrder.verify(voucherServiceMock).createPercentAmountVoucher(percent);
    }

    @DisplayName("BlackList 조회 테스트")
    @Test
    void blackList() {
        //given
        var consoleViewMock = mock(ConsoleView.class);
        var voucherServiceMock = mock(VoucherService.class);
        when(consoleViewMock.inputMenu()).thenReturn(Menu.BLACKLIST);

        var voucherController = new VoucherController(voucherServiceMock, consoleViewMock);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleViewMock);
        inOrder.verify(consoleViewMock).inputMenu();
        inOrder.verify(consoleViewMock).printCustomerBlackList();
    }

}