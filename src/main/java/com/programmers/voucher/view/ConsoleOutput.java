package com.programmers.voucher.view;

import com.programmers.voucher.controller.customer.dto.CustomerResponse;
import com.programmers.voucher.controller.voucher.dto.VoucherResponse;
import com.programmers.voucher.entity.voucher.VoucherType;
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
    private static final TextIO textIO = TextIoFactory.getTextIO();
    private static final TextTerminal<?> textTerminal = textIO.getTextTerminal();

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
    public void displayCreatedVoucher(VoucherResponse voucher) {
        textTerminal.print("New voucher created: ");
        displayVoucher(voucher);
    }

    @Override
    public void displayVoucher(VoucherResponse voucher) {
        textTerminal.println(voucher.toString());
    }

    @Override
    public void displayCustomer(CustomerResponse customer) {
        textTerminal.println(customer.toString());
    }

    private <T extends Enum<T>> void displayMenu(String title, T[] commands) {
        textTerminal.println(title);
        Arrays.stream(commands)
                .map(String::valueOf)
                .forEach(textTerminal::println);
    }
}
