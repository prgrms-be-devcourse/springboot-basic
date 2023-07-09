package com.programmers.voucher.global.io.textio;

import com.programmers.voucher.domain.customer.dto.CustomerDto;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.domain.voucher.util.VoucherErrorMessages;
import com.programmers.voucher.global.io.ConsoleOutput;
import com.programmers.voucher.global.io.command.CommandType;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import com.programmers.voucher.global.io.command.CustomerCommandType;
import com.programmers.voucher.global.io.command.VoucherCommandType;
import com.programmers.voucher.global.util.ConsoleMessages;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

import static com.programmers.voucher.global.util.ConsoleMessages.*;

@Component
public class TextIoOutput implements ConsoleOutput {
    private final TextIO textIO;

    public TextIoOutput(TextIO textIO) {
        this.textIO = textIO;
    }

    @Override
    public void printCommandSet() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.println(ConsoleMessages.VOUCHER_PROGRAM);
        printCommand(ConsoleCommandType.EXIT, EXIT_BEHAVIOR);
        printCommand(ConsoleCommandType.HELP, HELP_BEHAVIOR);
        printCommand(ConsoleCommandType.CUSTOMER, CUSTOMER_BEHAVIOR);
        printCommand(ConsoleCommandType.VOUCHER, VOUCHER_BEHAVIOR);
    }

    @Override
    public void printCustomerCommandSet() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.println(CUSTOMER_SERVICE);
        printCommand(CustomerCommandType.CREATE, CUSTOMER_CREATE_BEHAVIOR);
        printCommand(CustomerCommandType.LIST, CUSTOMER_LIST_BEHAVIOR);
        printCommand(CustomerCommandType.UPDATE, CUSTOMER_UPDATE_BEHAVIOR);
        printCommand(CustomerCommandType.DELETE, CUSTOMER_DELETE_BEHAVIOR);
        printCommand(CustomerCommandType.BLACKLIST, CUSTOMER_BLACKLIST_BEHAVIOR);
        printCommand(CustomerCommandType.HELP, HELP_BEHAVIOR);
        printCommand(CustomerCommandType.EXIT, EXIT_SERVICE_BEHAVIOR);
    }

    @Override
    public void printVoucherCommandSet() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.println(VOUCHER_SERVICE);
        printCommand(VoucherCommandType.CREATE, VOUCHER_CREATE_BEHAVIOR);
        printCommand(VoucherCommandType.LIST, VOUCHER_LIST_BEHAVIOR);
        printCommand(VoucherCommandType.DELETE, VOUCHER_DELETE_BEHAVIOR);
        printCommand(CustomerCommandType.HELP, HELP_BEHAVIOR);
        printCommand(CustomerCommandType.EXIT, EXIT_SERVICE_BEHAVIOR);
    }

    private void printCommand(CommandType commandType, String behavior) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.print(INPUT);
        textTerminal.executeWithPropertiesConfigurator(
                props -> props.setPromptBold(true),
                t -> t.print(" " + commandType.getType() + " ")
        );
        textTerminal.println(behavior);
    }

    @Override
    public void printVouchers(List<VoucherDto> vouchers) {
        vouchers.forEach(voucher ->
                print(voucherInfo(voucher)));
    }

    private String voucherInfo(VoucherDto voucherDto) {
        switch (voucherDto.getVoucherType()) {
            case FIXED_AMOUNT -> {
                return MessageFormat.format(
                        FIXED_VOUCHER_INFO,
                        voucherDto.getVoucherId(), voucherDto.getAmount());
            }
            case PERCENT -> {
                return MessageFormat.format(
                        PERCENT_VOUCHER_INFO,
                        voucherDto.getVoucherId(), voucherDto.getAmount());
            }
        }
        throw new IllegalStateException(VoucherErrorMessages.UNHANDLED_VOUCHER_TYPE);
    }

    @Override
    public void printCustomers(List<CustomerDto> customers) {
        customers.forEach(customer ->
                print(customerInfo(customer)));
    }


    private String customerInfo(CustomerDto customerDto) {
        String banned = customerDto.isBanned() ? "BAN" : "---";

        return MessageFormat.format(
                CUSTOMER_INFO,
                banned, customerDto.getCustomerId(), customerDto.getEmail(), customerDto.getName());
    }

    @Override
    public void print(String result) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println(result);
    }

    @Override
    public void exit() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println(EXIT_CONSOLE);
    }
}
