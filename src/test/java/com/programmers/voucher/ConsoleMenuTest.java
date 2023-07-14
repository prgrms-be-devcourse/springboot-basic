package com.programmers.voucher;

import com.programmers.voucher.global.io.Console;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import com.programmers.voucher.global.io.menu.ConsoleCustomerMenu;
import com.programmers.voucher.global.io.menu.ConsoleMenu;
import com.programmers.voucher.global.io.menu.ConsoleVoucherMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ConsoleMenuTest {

    @InjectMocks
    private ConsoleMenu consoleMenu;

    @Mock
    private Console console;

    @Mock
    private ConsoleCustomerMenu consoleCustomerMenu;

    @Mock
    private ConsoleVoucherMenu consoleVoucherMenu;

    @Test
    @DisplayName("성공: voucher 명령 입력")
    void commandTypeVoucher() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.VOUCHER);

        //when
        consoleMenu.runAndProcessClient();

        //then
        then(consoleVoucherMenu).should().runningVoucherService();
    }

    @Test
    @DisplayName("성공: customer 명령 입력")
    void commandTypeCustomer() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.CUSTOMER);

        //when
        consoleMenu.runAndProcessClient();

        //then
        then(consoleCustomerMenu).should().runningCustomerService();
    }

    @Test
    @DisplayName("성공: help 명령 입력")
    void commandTypeHelp() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.HELP);

        //when
        consoleMenu.runAndProcessClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should().printCommandSet();
    }

    @Test
    @DisplayName("성공: exit 명령 입력")
    void commandTypeExit() {
        //given
        given(console.inputInitialCommand()).willReturn(ConsoleCommandType.EXIT);

        //when
        consoleMenu.runAndProcessClient();

        //then
        then(console).should().inputInitialCommand();
        then(console).should().exit();
    }

    @Test
    @DisplayName("성공: IllegalArgumentException 발생 - true 반환(서비스 지속)")
    void run_ButThrownIllegalArgumentException_Then_KeepRunning() {
        //given
        given(console.inputInitialCommand())
                .willReturn(ConsoleCommandType.CUSTOMER, ConsoleCommandType.EXIT);
        doThrow(IllegalArgumentException.class)
                .when(consoleCustomerMenu).runningCustomerService();

        //when
        boolean keepRunningService = consoleMenu.runAndProcessClient();

        //then
        assertThat(keepRunningService).isTrue();
    }

    @Test
    @DisplayName("성공: NoSuchElementException 발생 - true 반환(서비스 지속)")
    void run_ButThrownNoSuchElementException_Then_KeepRunning() {
        //given
        given(console.inputInitialCommand())
                .willReturn(ConsoleCommandType.CUSTOMER, ConsoleCommandType.EXIT);
        doThrow(NoSuchElementException.class)
                .when(consoleCustomerMenu).runningCustomerService();

        //when
        boolean keepRunningService = consoleMenu.runAndProcessClient();

        //then
        assertThat(keepRunningService).isTrue();
    }

    @Test
    @DisplayName("성공: DuplicateKeyException 발생 - true 반환(서비스 지속)")
    void run_ButThrownDuplicateKeyException_Then_KeepRunning() {
        //given
        given(console.inputInitialCommand())
                .willReturn(ConsoleCommandType.CUSTOMER, ConsoleCommandType.EXIT);
        doThrow(DuplicateKeyException.class)
                .when(consoleCustomerMenu).runningCustomerService();

        //when
        boolean keepRunningService = consoleMenu.runAndProcessClient();

        //then
        assertThat(keepRunningService).isTrue();
    }

    @Test
    @DisplayName("성공: 예측하지 못한 예외 발생 - false 반환(서비스 중지)")
    void run_ButThrownTheOtherExceptions_Then_ExitConsole() {
        //given
        given(console.inputInitialCommand())
                .willReturn(ConsoleCommandType.CUSTOMER);
        doThrow(RuntimeException.class)
                .when(consoleCustomerMenu).runningCustomerService();

        //when
        boolean keepRunningService = consoleMenu.runAndProcessClient();

        //then
        assertThat(keepRunningService).isFalse();
    }
}