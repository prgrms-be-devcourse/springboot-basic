package com.programmers.voucher.view;

import com.programmers.voucher.controller.voucher.dto.VoucherCreateResponse;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.view.dto.Command;
import com.programmers.voucher.view.dto.VoucherCommand;
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
        displayMenu("=== Voucher Program ===", Command.values());
    }

    @Override
    public void displayVoucherCommands() {
        displayMenu("=== 바우처 관리 메뉴 ===", VoucherCommand.values());
    }

    @Override
    public void displayVoucherType() {
        displayMenu("=== 바우처 타입 ===", VoucherType.values());
    }

    private <T extends Enum<T>> void displayMenu(String title, T[] commands) {
        textTerminal.println(title);
        Arrays.stream(commands)
                .map(String::valueOf)
                .forEach(textTerminal::println);
    }

    @Override
    public void displayCreatedVoucher(VoucherCreateResponse voucher) {
        textTerminal.print("New voucher created: ");
        textTerminal.println(voucher.toString());
    }

    @Override
    public void displayVoucher(Voucher voucher) {
        textTerminal.println(voucher.toString());
    }
}
