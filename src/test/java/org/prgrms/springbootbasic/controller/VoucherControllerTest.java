package org.prgrms.springbootbasic.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.springbootbasic.VoucherType;
import org.prgrms.springbootbasic.service.CustomerService;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.view.ConsoleView;

@ExtendWith(MockitoExtension.class)
class VoucherControllerTest {

    @Mock
    ConsoleView consoleView;

    @Mock
    VoucherService voucherService;

    @Mock
    CustomerService customerService;

    VoucherController voucherController;

    @BeforeEach
    void init() {
        voucherController = new VoucherController(voucherService, consoleView, customerService);
    }

    @DisplayName("EXIT 테스트")
    @Test
    void exit() {
        //given
        when(consoleView.inputMenu()).thenReturn(Menu.EXIT);

        //when
        boolean actual = voucherController.process();

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("LIST 테스트")
    @Test
    void list() {
        //given
        when(consoleView.inputMenu()).thenReturn(Menu.LIST);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleView, voucherService);
        inOrder.verify(consoleView).inputMenu();
        inOrder.verify(voucherService).findAll();
        inOrder.verify(consoleView).printList(anyList());
    }

    @DisplayName("FixedAmountVoucher 생성 테스트")
    @Test
    void createFixedAmountVoucher() {
        //given
        when(consoleView.inputMenu()).thenReturn(Menu.CREATE);
        when(consoleView.selectVoucherType()).thenReturn(VoucherType.FIXED);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleView, voucherService);
        inOrder.verify(consoleView).inputMenu();
        inOrder.verify(consoleView).selectVoucherType();
        inOrder.verify(consoleView).selectAmount();
        inOrder.verify(voucherService).createFixedAmountVoucher(anyInt());
    }

    @DisplayName("PercentAmountVoucher 생성 테스트")
    @Test
    void createPercentAmountVoucher() {
        //given
        when(consoleView.inputMenu()).thenReturn(Menu.CREATE);
        when(consoleView.selectVoucherType()).thenReturn(VoucherType.PERCENT);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleView, voucherService);
        inOrder.verify(consoleView).inputMenu();
        inOrder.verify(consoleView).selectVoucherType();
        inOrder.verify(consoleView).selectPercent();
        inOrder.verify(voucherService).createPercentAmountVoucher(anyInt());
    }

    @DisplayName("BlackList 조회 테스트")
    @Test
    void blackList() {
        //given
        when(consoleView.inputMenu()).thenReturn(Menu.BLACKLIST);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleView);
        inOrder.verify(consoleView).inputMenu();
        inOrder.verify(consoleView).printCustomerBlackList();
    }

    @DisplayName("createCustomer 테스트")
    @Test
    void createCustomer() {
        //given
        when(consoleView.inputMenu()).thenReturn(Menu.CREATECUSTOMER);
        when(consoleView.selectName()).thenReturn("");
        when(consoleView.selectEmail()).thenReturn("");

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleView, customerService);
        inOrder.verify(consoleView).inputMenu();
        inOrder.verify(consoleView).selectName();
        inOrder.verify(consoleView).selectEmail();
        inOrder.verify(customerService).createCustomer(anyString(), anyString());
    }

    @DisplayName("assignVoucher 테스트 - 정상")
    @Test
    void assignVoucher() {
        //given
        when(consoleView.inputMenu()).thenReturn(Menu.ASSIGNVOUCHER);
        when(consoleView.selectVoucherId()).thenReturn(UUID.randomUUID());
        when(consoleView.selectCustomerId()).thenReturn(UUID.randomUUID());

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleView, voucherService);
        inOrder.verify(consoleView).selectVoucherId();
        inOrder.verify(consoleView).selectCustomerId();
        inOrder.verify(voucherService).assignVoucherToCustomer(any(UUID.class), any(UUID.class));
    }

    @DisplayName("listCustomerVoucher 테스트 - 정상 케이스")
    @Test
    void listCustomerVoucher() {
        //given
        when(consoleView.inputMenu()).thenReturn(Menu.LISTCUSTOMERVOUCHER);
        UUID customerId = UUID.randomUUID();
        when(consoleView.selectCustomerId()).thenReturn(customerId);
        when(voucherService.findCustomerVoucher(customerId)).thenReturn(Collections.emptyList());

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleView, voucherService);
        inOrder.verify(consoleView).selectCustomerId();
        inOrder.verify(voucherService).findCustomerVoucher(customerId);
    }

    @DisplayName("deleteCustomerVoucher 테스트 - 정상 케이스")
    @Test
    void deleteCustomerVoucher() {
        //given
        when(consoleView.inputMenu()).thenReturn(Menu.DELETECUSTOMERVOUCHER);
        when(consoleView.selectCustomerId()).thenReturn(UUID.randomUUID());
        when(consoleView.selectVoucherId()).thenReturn(UUID.randomUUID());

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(consoleView, customerService);
        inOrder.verify(consoleView).selectCustomerId();
        inOrder.verify(consoleView).selectVoucherId();
        inOrder.verify(customerService).deleteVoucher(any(UUID.class), any(UUID.class));
    }
}