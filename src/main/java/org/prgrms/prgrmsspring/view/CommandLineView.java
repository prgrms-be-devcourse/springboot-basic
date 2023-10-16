package org.prgrms.prgrmsspring.view;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgrms.prgrmsspring.domain.Command;
import org.prgrms.prgrmsspring.domain.VoucherType;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class CommandLineView {

    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    public Command getCommand() {
        textTerminal.println("=== Voucher Program ===");
        for (Command command : Command.values()) {
            textTerminal.printf("Type %s to %s\n", command.name().toLowerCase(), command.getDocument());
        }
        try {
            return Command.of(inputCommand());
        } catch (IllegalArgumentException e) {
            textTerminal.print(e.getMessage());
            textTerminal.abort();
            return Command.EXIT;
        }
    }

    public <T> void printAll(List<T> list) {
        list.forEach(l -> textTerminal.println(l.toString()));
    }

    public Voucher createVoucher() {
        Arrays.stream(VoucherType.values()).forEach(v -> textTerminal.println("%d. %s".formatted((v.ordinal() + 1), v.getName())));
        Integer voucherModeNum = textIO.newIntInputReader().read("Input number");
        VoucherType voucherType = VoucherType.of(voucherModeNum);
        Long value = textIO.newLongInputReader().read("Input Value");
        return voucherType.constructVoucher(UUID.randomUUID(), value);
    }

    private String inputCommand() {
        return textIO.newStringInputReader().read();
    }

}
