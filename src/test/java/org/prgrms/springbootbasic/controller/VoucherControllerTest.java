package org.prgrms.springbootbasic.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
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
import org.prgrms.springbootbasic.entity.customer.Email;
import org.prgrms.springbootbasic.entity.customer.Name;
import org.prgrms.springbootbasic.service.CustomerService;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.view.View;

@ExtendWith(MockitoExtension.class)
class VoucherControllerTest {

    @Mock
    View view;

    @Mock
    VoucherService voucherService;

    @Mock
    CustomerService customerService;

    VoucherController voucherController;

    @BeforeEach
    void init() {
        voucherController = new VoucherController(voucherService, view, customerService);
    }

    @DisplayName("EXIT 테스트")
    @Test
    void exit() {
        //given
        when(view.inputMenu()).thenReturn(Menu.EXIT);

        //when
        boolean actual = voucherController.process();

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("LIST 테스트")
    @Test
    void list() {
        //given
        when(view.inputMenu()).thenReturn(Menu.LIST);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(view, voucherService);
        inOrder.verify(view).inputMenu();
        inOrder.verify(voucherService).findAll();
        inOrder.verify(view).printList(anyList());
    }

    @DisplayName("FixedAmountVoucher 생성 테스트")
    @Test
    void createFixedAmountVoucher() {
        //given
        when(view.inputMenu()).thenReturn(Menu.CREATE);
        when(view.selectVoucherType()).thenReturn(VoucherType.FIXED);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(view, voucherService);
        inOrder.verify(view).inputMenu();
        inOrder.verify(view).selectVoucherType();
        inOrder.verify(view).selectAmount();
        inOrder.verify(voucherService).createVoucher(any(VoucherType.class), anyInt(), anyInt());
    }

    @DisplayName("PercentAmountVoucher 생성 테스트")
    @Test
    void createPercentAmountVoucher() {
        //given
        when(view.inputMenu()).thenReturn(Menu.CREATE);
        when(view.selectVoucherType()).thenReturn(VoucherType.PERCENT);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(view, voucherService);
        inOrder.verify(view).inputMenu();
        inOrder.verify(view).selectVoucherType();
        inOrder.verify(view).selectPercent();
        inOrder.verify(voucherService).createVoucher(any(VoucherType.class), anyInt(), anyInt());
    }

    @DisplayName("BlackList 조회 테스트")
    @Test
    void blackList() {
        //given
        when(view.inputMenu()).thenReturn(Menu.BLACKLIST);

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(view);
        inOrder.verify(view).inputMenu();
        inOrder.verify(view).printCustomerBlackList();
    }

    @DisplayName("createCustomer 테스트")
    @Test
    void createCustomer() {
        //given
        when(view.inputMenu()).thenReturn(Menu.CREATECUSTOMER);
        when(view.selectName()).thenReturn("test");
        when(view.selectEmail()).thenReturn("test@gmail.com");

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(view, customerService);
        inOrder.verify(view).inputMenu();
        inOrder.verify(view).selectName();
        inOrder.verify(view).selectEmail();
        inOrder.verify(customerService).createCustomer(any(Name.class), any(Email.class));
    }

    @DisplayName("assignVoucher 테스트 - 정상")
    @Test
    void assignVoucher() {
        //given
        when(view.inputMenu()).thenReturn(Menu.ASSIGNVOUCHER);
        when(view.selectVoucherId()).thenReturn(UUID.randomUUID());
        when(view.selectCustomerId()).thenReturn(UUID.randomUUID());

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(view, voucherService);
        inOrder.verify(view).selectVoucherId();
        inOrder.verify(view).selectCustomerId();
        inOrder.verify(voucherService).assignVoucherToCustomer(any(UUID.class), any(UUID.class));
    }

    @DisplayName("listCustomerVoucher 테스트 - 정상 케이스")
    @Test
    void listCustomerVoucher() {
        //given
        when(view.inputMenu()).thenReturn(Menu.LISTCUSTOMERVOUCHER);
        UUID customerId = UUID.randomUUID();
        when(view.selectCustomerId()).thenReturn(customerId);
        when(voucherService.findCustomerVoucher(customerId)).thenReturn(Collections.emptyList());

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(view, voucherService);
        inOrder.verify(view).selectCustomerId();
        inOrder.verify(voucherService).findCustomerVoucher(customerId);
    }

    @DisplayName("deleteCustomerVoucher 테스트 - 정상 케이스")
    @Test
    void deleteCustomerVoucher() {
        //given
        when(view.inputMenu()).thenReturn(Menu.DELETECUSTOMERVOUCHER);
        when(view.selectCustomerId()).thenReturn(UUID.randomUUID());
        when(view.selectVoucherId()).thenReturn(UUID.randomUUID());

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(view, customerService);
        inOrder.verify(view).selectCustomerId();
        inOrder.verify(view).selectVoucherId();
        inOrder.verify(customerService).deleteVoucher(any(UUID.class), any(UUID.class));
    }

    @DisplayName("listCustomerHavingSpecificVoucherType 테스트")
    @Test
    void listCustomerHavingSpecificVoucherType() {
        //given
        when(view.inputMenu()).thenReturn(Menu.LISTCUSTOMERHAVINGSEPCIFICVOUCHERTYPE);
        when(view.selectVoucherType()).thenReturn(VoucherType.FIXED);
        when(customerService.findCustomerHavingSpecificVoucherType(VoucherType.FIXED))
            .thenReturn(Collections.emptyList());

        //when
        voucherController.process();

        //then
        var inOrder = inOrder(view, customerService);
        inOrder.verify(view).selectVoucherType();
        inOrder.verify(customerService).findCustomerHavingSpecificVoucherType(VoucherType.FIXED);
        inOrder.verify(view).printAllCustomers(anyList());
    }
}