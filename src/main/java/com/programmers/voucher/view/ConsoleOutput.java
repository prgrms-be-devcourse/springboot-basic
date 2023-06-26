package com.programmers.voucher.view;

import com.programmers.voucher.constant.Style;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.VoucherCommand;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.Arrays;

public class ConsoleOutput implements Output {
    private static final TextIO textIO = TextIoFactory.getTextIO();
    private static final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    @Override
    public void displayCommands() {
        textTerminal.println("=== Voucher Program ===");

        Arrays.stream(Command.values())
                .forEach(command -> { //TODO 반복 코드 개선
                    textTerminal.print("Type ");
                    textTerminal.print(Style.apply(command.getCode(), Style.BOLD));
                    textTerminal.println(" to " + command.getText());
                });
    }

    @Override
    public void displayVoucherCommands() {
        Arrays.stream(VoucherCommand.values())
                .forEach(command -> {
                    textTerminal.print("Type ");
                    textTerminal.print(Style.apply(command.getCode(), Style.BOLD));
                    textTerminal.println(" to " + command.getText());
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
