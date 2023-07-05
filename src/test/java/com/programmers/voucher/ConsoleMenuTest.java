package com.programmers.voucher;

import com.programmers.voucher.domain.customer.controller.CustomerController;
import com.programmers.voucher.domain.voucher.controller.VoucherController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import com.programmers.voucher.global.io.menu.ConsoleMenu;
import com.programmers.voucher.global.io.command.CustomerCommandType;
import com.programmers.voucher.global.io.command.VoucherCommandType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleMenuTest {

    @InjectMocks
    private ConsoleMenu consoleMenu;

    @Mock
    private Console console;

    @Mock
    private CustomerController customerController;

    @Mock
    private VoucherController voucherController;


    @Test
    @DisplayName("성공: voucher 명령 입력 - create 명령 입력 - exit 명령 입력")
    public void voucherCommandTypeCreate() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.VOUCHER);
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.CREATE, VoucherCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).inputVoucherCommandType();
        then(voucherController).should().createVoucher();
    }

    @Test
    @DisplayName("성공: voucher 명령 입력 - list 명령 입력 - exit 명령 입력")
    void voucherCommandTypeList() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.VOUCHER);
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.LIST, VoucherCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).inputVoucherCommandType();
        then(voucherController).should().findVouchers();
    }

    @Test
    @DisplayName("성공: voucher 명령 입력 - help 명령 입력 - exit 명령 입력")
    void voucherCommandTypeHelp() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.VOUCHER);
        given(console.inputVoucherCommandType())
                .willReturn(VoucherCommandType.HELP, VoucherCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).inputVoucherCommandType();
        then(console).should(times(2)).printVoucherCommandSet();
    }

    @Test
    @DisplayName("성공: customer 명령 입력 - blacklist 명령 입력 - exit 명령 입력")
    void customerCommandTypeBlacklist() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CUSTOMER);
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.BLACKLIST, CustomerCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).inputCustomerCommandType();
        then(customerController).should().findBlacklistCustomers();
    }

    @Test
    @DisplayName("성공: customer 명령 입력 - create 명령 입력 - exit 명령 입력")
    void customerCommandTypeCreate() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CUSTOMER);
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.CREATE, CustomerCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).inputCustomerCommandType();
        then(customerController).should().createCustomer();
    }

    @Test
    @DisplayName("성공: customer 명령 입력 - list 명령 입력 - exit 명령 입력")
    void customerCommandTypeList() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CUSTOMER);
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.LIST, CustomerCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).inputCustomerCommandType();
        then(customerController).should().findCustomers();
    }

    @Test
    @DisplayName("성공: customer 명령 입력 - update 명령 입력 - exit 명령 입력")
    void customerCommandTypeUpdate() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CUSTOMER);
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.UPDATE, CustomerCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).inputCustomerCommandType();
        then(customerController).should().updateCustomer();
    }

    @Test
    @DisplayName("성공: customer 명령 입력 - delete 명령 입력 - exit 명령 입력")
    void customerCommandTypeDelete() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CUSTOMER);
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.DELETE, CustomerCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).inputCustomerCommandType();
        then(customerController).should().deleteCustomer();
    }

    @Test
    @DisplayName("성공: customer 명령 입력 - help 명령 입력 - exit 명령 입력")
    void customerCommandTypeHelp() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CUSTOMER);
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.HELP, CustomerCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).inputCustomerCommandType();
        then(console).should(times(2)).printCustomerCommandSet();
    }

    @Test
    @DisplayName("성공: help 명령 입력")
    void commandTypeHelp() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.HELP);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should(times(2)).printCommandSet();
    }

    @Test
    @DisplayName("성공: exit 명령 입력")
    void commandTypeExit() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.EXIT);

        //when
        consoleMenu.runClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should().exit();
    }
}