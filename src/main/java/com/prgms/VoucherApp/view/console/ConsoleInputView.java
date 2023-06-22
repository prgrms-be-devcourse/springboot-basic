package com.prgms.VoucherApp.view.console;

import com.prgms.VoucherApp.view.Input;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class ConsoleInputView implements Input {

    private final TextIO textIO;

    public ConsoleInputView() {
        textIO = TextIoFactory.getTextIO();
    }

    @Override
    public String inputCommand() {
        return textIO.newStringInputReader()
                .withInputTrimming(true)
                .withInlinePossibleValues("exit", "create", "list")
                .read("명령어를 입력해주세요.");
    }

    @Override
    public String inputVoucherPolicy() {
        return null;
    }

    @Override
    public String inputDiscountAmount() {
        return null;
    }
}
