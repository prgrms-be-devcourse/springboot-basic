package com.programmers.voucher.global.io.menu;

import com.programmers.voucher.domain.customer.controller.CustomerConsoleController;
import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.CustomerCommandType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ConsoleCustomerMenuTest {

    @InjectMocks
    private ConsoleCustomerMenu consoleCustomerMenu;

    @Mock
    private Console console;

    @Mock
    private CustomerConsoleController customerController;

    @Test
    @DisplayName("성공: blacklist 명령 실행 - exit 명령 실행")
    void customerCommandTypeBlacklist() {
        //given
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.BLACKLIST, CustomerCommandType.EXIT);

        //when
        consoleCustomerMenu.runningCustomerService();

        //then
        then(customerController).should().findBlacklistCustomers();
        then(console).should().printCustomers(any());
    }

    @Test
    @DisplayName("성공: create 명령 실행 - exit 명령 실행")
    void customerCommandTypeCreate() {
        //given
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.CREATE, CustomerCommandType.EXIT);

        //when
        consoleCustomerMenu.runningCustomerService();

        //then
        then(console).should().inputCustomerCreateInfo();
        then(customerController).should().createCustomer(any());
        then(console).should().print(any());
    }

    @Test
    @DisplayName("성공: list 명령 실행 - exit 명령 실행")
    void customerCommandTypeList() {
        //given
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.LIST, CustomerCommandType.EXIT);

        //when
        consoleCustomerMenu.runningCustomerService();

        //then
        then(customerController).should().findCustomers();
        then(console).should().printCustomers(any());
    }

    @Test
    @DisplayName("성공: update 명령 실행 - exit 명령 실행")
    void customerCommandTypeUpdate() {
        //given
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.UPDATE, CustomerCommandType.EXIT);

        //when
        consoleCustomerMenu.runningCustomerService();

        //then
        then(console).should().inputCustomerUpdateInfo();
        then(customerController).should().updateCustomer(any());
        then(console).should().print(any());
    }

    @Test
    @DisplayName("성공: delete 명령 실행 - exit 명령 실행")
    void customerCommandTypeDelete() {
        //given
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.DELETE, CustomerCommandType.EXIT);

        //when
        consoleCustomerMenu.runningCustomerService();

        //then
        then(console).should().inputUUID();
        then(customerController).should().deleteCustomer(any());
        then(console).should().print(any());
    }

    @Test
    @DisplayName("성공: help 명령 실행 - exit 명령 실행")
    void customerCommandTypeHelp() {
        //given
        given(console.inputCustomerCommandType())
                .willReturn(CustomerCommandType.HELP, CustomerCommandType.EXIT);

        //when
        consoleCustomerMenu.runningCustomerService();

        //then
        then(console).should(times(2)).printCustomerCommandSet();
    }
}