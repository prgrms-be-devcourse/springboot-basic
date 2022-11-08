package prgms.vouchermanagementapp.io;

import prgms.vouchermanagementapp.io.message.SystemMessage;
import prgms.vouchermanagementapp.voucher.VoucherType;

import java.util.List;

public class Writer {

    private static final String DOT = ".";
    private static final String BLANK = " ";
    private static final String PROMPT_SIGNATURE = "> ";

    private static final String VOUCHER_GUIDE = "Enter number you want to create.";

    public void print(List<String> list) {
        list.forEach(System.out::println);
    }

    public void printCommandGuide() {
        printBlankLine();
        print(CommandType.getMessages());
        printPromptSignature();
    }

    public void printVoucherTypeGuide() {
        printBlankLine();
        System.out.println(VOUCHER_GUIDE);

        List<VoucherType> voucherTypes = VoucherType.getValues();
        voucherTypes.forEach(voucherType -> {
            String guide = voucherType.getIndex() + DOT + BLANK + voucherType;
            System.out.println(guide);
        });
        printPromptSignature();
    }

    public void printExitMessage() {
        System.out.println(SystemMessage.EXIT.getMessage());
    }

    private void printPromptSignature() {
        System.out.print(PROMPT_SIGNATURE);
    }

    private void printBlankLine() {
        System.out.println();
    }
}
