package com.prgms.VoucherApp.view.console;

import com.prgms.VoucherApp.domain.VoucherPolicy;
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
                .read("명령어를 입력해주세요 >>");
    }

    @Override
    public String inputVoucherPolicy() {
        return textIO.newStringInputReader()
                .withInputTrimming(true)
                .withInlinePossibleValues("fix", "percent")
                .read("명령어를 입력해주세요 >>");
    }

    @Override
    public Long inputDiscountAmount(VoucherPolicy policy) {
        return switch (policy) {
            case FIXED_VOUCHER -> textIO.newLongInputReader()
                    .withInputTrimming(true)
                    .withMinVal(0L)
                    .read("할인 금액을 입력해주세요 >>");
            case PERCENT_VOUCHER -> textIO.newLongInputReader()
                    .withInputTrimming(true)
                    .withMinVal(0L)
                    .withMaxVal(100L)
                    .read("할인 비율을 입력해주세요 >>");
            default -> 0L;
        };
    }
}
