package com.programmers.voucher.view;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.VoucherType;
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
        textTerminal.println("=== Voucher Program ===");

        Arrays.stream(Command.values())
                .forEach(command -> {
                    textTerminal.print("Type ");
                    textTerminal.print(ConsoleStyle.bold(command.getName()));
                    textTerminal.println(" to " + command.getText());
                });
    }

    @Override
    public void displayVoucherType() {
        Arrays.stream(VoucherType.values())
                .forEach(voucher -> {
                    textTerminal.print("Type ");
                    textTerminal.print(ConsoleStyle.bold(voucher.getName()));
                    textTerminal.println(" to " + voucher.getText());
                });
    }

    @Override
    public void displayCreatedVoucher(Voucher voucher) {
        textTerminal.print("New voucher created: ");
        displayVoucher(voucher);
    }

    @Override
    public void displayVoucher(Voucher voucher) {
        textTerminal.println(voucher.toString());
    }
}
