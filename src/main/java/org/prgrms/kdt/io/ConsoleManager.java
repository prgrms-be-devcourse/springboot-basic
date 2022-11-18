package org.prgrms.kdt.io;

import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsoleManager {
    public static final String COMMAND_LIST_PROMPT = System.lineSeparator() +
            "=== Voucher Program ===" + System.lineSeparator() +
            "Type exit to exit the program." + System.lineSeparator() +
            "Type create to create a new voucher." + System.lineSeparator() +
            "Type list to list all vouchers" + System.lineSeparator() +
            "Type black to show black customer list" + System.lineSeparator();
    public static final String VOUCHER_TYPE_PROMPT = System.lineSeparator() + "Enter voucher type. (fixed or percent)";
    public static final String VOUCHER_AMOUNT_PROMPT = "Enter a value. (fixed - 0~ | percent - 0~100)";
    public static final String DELIMITER = ", ";

    private final Input input;
    private final Output output;

    public ConsoleManager(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public String getCommand() {
        output.printText(COMMAND_LIST_PROMPT);

        return input.inputText();
    }

    public String getType() {
        output.printText(VOUCHER_TYPE_PROMPT);

        return input.inputText();
    }

    public String getVoucherAmount() {
        output.printText(VOUCHER_AMOUNT_PROMPT);

        return input.inputText();
    }

    public void printError(String message) {
        output.printText(message);
    }

    public void printVouchers(List<Voucher> vouchers) {
        output.printText(
                vouchers.stream()
                        .map(voucher -> voucher.getType().getType() + DELIMITER + voucher.getAmount().getValue())
                        .collect(Collectors.joining("\n"))
        );
    }

    public void printBlackList(List<Customer> blacklist) {
        output.printText(
                blacklist.stream()
                        .map(customer -> customer.getId() + DELIMITER + customer.getName())
                        .collect(Collectors.joining("\n"))
        );
    }
}
