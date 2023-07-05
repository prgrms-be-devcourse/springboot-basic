package com.programmers.voucher.global.io.textio;

import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.util.VoucherErrorMessages;
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

import java.text.MessageFormat;
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
    @DisplayName("성공: customer 메뉴 명령어 목록 출력")
    void printCustomerCommandSet() {
        //given
        //when
        textIoOutput.printCustomerCommandSet();

        //then
        String output = mockTextTerminal.getOutput();
        String expected =
                CUSTOMER_SERVICE + "\n"
                + commandMessage(CustomerCommandType.CREATE, CUSTOMER_CREATE_BEHAVIOR) + "\n"
                + commandMessage(CustomerCommandType.LIST, CUSTOMER_LIST_BEHAVIOR) + "\n"
                + commandMessage(CustomerCommandType.UPDATE, CUSTOMER_UPDATE_BEHAVIOR) + "\n"
                + commandMessage(CustomerCommandType.DELETE, CUSTOMER_DELETE_BEHAVIOR) + "\n"
                + commandMessage(CustomerCommandType.BLACKLIST, CUSTOMER_BLACKLIST_BEHAVIOR) + "\n"
                + commandMessage(CustomerCommandType.HELP, HELP_BEHAVIOR) + "\n"
                + commandMessage(CustomerCommandType.EXIT, EXIT_SERVICE_BEHAVIOR);
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
        VoucherDto fixedVoucher = new VoucherDto(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 10);
        VoucherDto percentVoucher = new VoucherDto(UUID.randomUUID(), VoucherType.PERCENT, 10);
        List<VoucherDto> givenVouchers = List.of(fixedVoucher, percentVoucher);

        //when
        textIoOutput.printVouchers(givenVouchers);

        //then
        String expectedOutput = voucherInfo(fixedVoucher) + "\n" + voucherInfo(percentVoucher);
        String output = mockTextTerminal.getOutput();

        assertThat(output).isEqualTo(expectedOutput);
    }

    private String voucherInfo(VoucherDto voucherDto) {
        switch (voucherDto.getVoucherType()) {
            case FIXED_AMOUNT -> {
                return MessageFormat.format(
                        "VoucherId: {0}, Amount: {1}$",
                        voucherDto.getVoucherId(), voucherDto.getAmount());
            }
            case PERCENT -> {
                return MessageFormat.format(
                        "VoucherId: {0}, Percent: {1}%",
                        voucherDto.getVoucherId(), voucherDto.getAmount());
            }
        }

        throw new IllegalStateException(VoucherErrorMessages.UNHANDLED_VOUCHER_TYPE);
    }

    @Test
    @DisplayName("성공: 회원 목록 출력")
    void printCustomers() {
        //given
        CustomerDto customer =
                new CustomerDto(UUID.randomUUID(), "customer@gmail.com", "customer", false);
        CustomerDto bannedCustomer =
                new CustomerDto(UUID.randomUUID(), "banned@gmail.com", "banned", true);
        List<CustomerDto> givenCustomers = List.of(customer, bannedCustomer);

        //when
        textIoOutput.printCustomers(givenCustomers);

        //then
        String expectedOutput = customerInfo(customer) + "\n" + customerInfo(bannedCustomer);
        String output = mockTextTerminal.getOutput();

        assertThat(output).isEqualTo(expectedOutput);
    }

    private String customerInfo(CustomerDto customerDto) {
        String banned = customerDto.isBanned() ? "BAN" : "---";

        return MessageFormat.format(
                "[{0}] CustomerId: {1}, Email: {2}, Name: {3}",
                banned, customerDto.getCustomerId(), customerDto.getEmail(), customerDto.getName());
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