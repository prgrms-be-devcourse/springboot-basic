package com.programmers.voucher.console;

import com.programmers.voucher.domain.Type;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class TextIoConsole implements Console {
    private TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public Type getCondition() {
        System.out.println();
        String stringType = textIO.newStringInputReader()
                .read("=== Voucher Program === \n" +
                        "Type exit to exit the program \n" +
                        "Type create to create a new voucher. \n" +
                        "Type list to list all vouchers. \n" +
                        "[enter type] : ");
        return Type.validateInput(stringType);
    }
}
