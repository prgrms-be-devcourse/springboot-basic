package com.programmers.voucher.view;

import com.programmers.voucher.domain.customer.dto.CustomerResponse;
import com.programmers.voucher.domain.voucher.dto.VoucherResponse;
import com.programmers.voucher.domain.voucher.entity.VoucherType;
import com.programmers.voucher.view.command.Command;
import com.programmers.voucher.view.command.CustomerCommand;
import com.programmers.voucher.view.command.VoucherCommand;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ConsoleOutput implements Output {
    private static final TextIO TEXT_IO = TextIoFactory.getTextIO();
    private static final TextTerminal<?> TEXT_TERMINAL = TEXT_IO.getTextTerminal();

    @Override
    public void displayCommands() {
        displayMenu("=== Voucher Program ===", Command.values());
    }

    @Override
    public void displayVoucherCommands() {
        displayMenu("=== Voucher Menu ===", VoucherCommand.values());
    }

    @Override
    public void displayCustomerCommands() {
        displayMenu("=== Customer Menu ===", CustomerCommand.values());
    }

    @Override
    public void displayVoucherType() {
        displayMenu("=== Voucher Type ===", VoucherType.values());
    }

    @Override
    public void displayVoucher(VoucherResponse voucher) {
        TEXT_TERMINAL.println(voucher.toString());
    }

    @Override
    public void displayCustomer(CustomerResponse customer) {
        TEXT_TERMINAL.println(customer.toString());
    }

    private <T extends Enum<T>> void displayMenu(String title, T[] commands) {
        TEXT_TERMINAL.println(title);
        Arrays.stream(commands)
                .map(String::valueOf)
                .forEach(TEXT_TERMINAL::println);
    }
}
