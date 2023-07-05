package com.programmers.voucher.global.io.textio;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.global.io.command.CommandType;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import com.programmers.voucher.global.io.command.CustomerCommandType;
import com.programmers.voucher.global.io.command.VoucherCommandType;
import com.programmers.voucher.global.util.ConsoleMessages;
import org.beryx.textio.TextIO;
import org.beryx.textio.mock.MockTextTerminal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.global.util.ConsoleMessages.*;
import static org.assertj.core.api.Assertions.assertThat;

class TextIoOutputTest {
    private MockTextTerminal mockTextTerminal;
    private TextIO textIO;

    private TextIoOutput textIoOutput;

    @BeforeEach
    void init() {
        mockTextTerminal = new MockTextTerminal();
        textIO = new TextIO(mockTextTerminal);

        textIoOutput = new TextIoOutput(textIO);
    }

    @Test
    @DisplayName("성공: 탑 메뉴 명령어 목록 출력")
    void printCommandSet() {
        //given
        //when
        textIoOutput.printCommandSet();

        //then
        String output = mockTextTerminal.getOutput();
        String expected =
                ConsoleMessages.VOUCHER_PROGRAM + "\n"
                + commandMessage(ConsoleCommandType.EXIT, EXIT_BEHAVIOR) + "\n"
                + commandMessage(ConsoleCommandType.HELP, HELP_BEHAVIOR) + "\n"
                + commandMessage(ConsoleCommandType.CUSTOMER, CUSTOMER_BEHAVIOR) + "\n"
                + commandMessage(ConsoleCommandType.VOUCHER, VOUCHER_BEHAVIOR);
        assertThat(output).isEqualTo(expected);
    }


    @Test
    @DisplayName("성공: voucher 메뉴 명령어 목록 출력")
    void printVoucherCommandSet() {
        //given
        //when
        textIoOutput.printVoucherCommandSet();

        //then
        String output = mockTextTerminal.getOutput();
        String expected =
                VOUCHER_SERVICE + "\n"
                + commandMessage(VoucherCommandType.CREATE, VOUCHER_CREATE_BEHAVIOR) + "\n"
                + commandMessage(VoucherCommandType.LIST, VOUCHER_LIST_BEHAVIOR) + "\n"
                + commandMessage(VoucherCommandType.DELETE, VOUCHER_DELETE_BEHAVIOR) + "\n"
                + commandMessage(VoucherCommandType.HELP, HELP_BEHAVIOR) + "\n"
                + commandMessage(VoucherCommandType.EXIT, EXIT_SERVICE_BEHAVIOR);
        assertThat(output).isEqualTo(expected);
    }

    private String commandMessage(CommandType commandType, String behavior) {
        return ConsoleMessages.INPUT + " " + commandType.getType() + " " + behavior;
    }

    @Test
    @DisplayName("성공: 바우처 목록 출력")
    void printVouchers() {
        //given
        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10);
        PercentDiscountVoucher percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
        List<Voucher> givenVouchers = List.of(fixedVoucher, percentVoucher);

        //when
        textIoOutput.printVouchers(givenVouchers);

        //then
        String expectedOutput = fixedVoucher.fullInfoString() + "\n" + percentVoucher.fullInfoString();
        String output = mockTextTerminal.getOutput();

        assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("성공: 회원 목록 출력")
    void printCustomers() {
        //given
        Customer customerA = new Customer(UUID.randomUUID(), "customerA@gmail.com", "customerA");
        Customer customerB = new Customer(UUID.randomUUID(), "customerB@gmail.com", "customerB");
        List<Customer> givenCustomers = List.of(customerA, customerB);

        //when
        textIoOutput.printCustomers(givenCustomers);

        //then
        String expectedOutput = customerA.fullInfoString() + "\n" + customerB.fullInfoString();
        String output = mockTextTerminal.getOutput();

        assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("성공: 문자열 출력")
    void print() {
        //given
        String input = "Hello, world!";

        //when
        textIoOutput.print(input);

        //then
        String output = mockTextTerminal.getOutput();
        assertThat(output).isEqualTo(input);
    }

    @Test
    @DisplayName("성공: 콘솔 종료 메시지 출력")
    void exit() {
        //given
        //when
        textIoOutput.exit();

        //then
        String output = mockTextTerminal.getOutput();
        assertThat(output).isEqualTo(EXIT_CONSOLE);
    }
}