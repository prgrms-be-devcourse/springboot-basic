package prgms.vouchermanagementapp.io;

import prgms.vouchermanagementapp.io.message.SystemMessage;
import prgms.vouchermanagementapp.voucher.VoucherType;

import java.util.List;

public class Writer {

    private static final String DOT = ".";
    private static final String BLANK = " ";
    private static final String PROMPT_SIGNATURE = "> ";

    private static final String VOUCHER_GUIDE = "Enter index of voucher type you want to create.";
    private static final String FIXED_AMOUNT_GUIDE = "Enter fixed amount you want to get a discount.";
    private static final String FIXED_DISCOUNT_RATIO_GUIDE = "Enter fixed ratio you want to get a discount.";

    public void print(List<String> list) {
        list.forEach(System.out::println);
    }

    public void printCommandGuide() {
        printBlankLine();

        List<CommandType> commandTypes = CommandType.getCommandTypes();
        commandTypes.forEach(commandType -> System.out.println(commandType.getMessage()));

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

    public void printFixedAmountGuide() {
        printBlankLine();
        System.out.println(FIXED_AMOUNT_GUIDE);
        printPromptSignature();
    }

    public void printFixedDiscountRatioGuide() {
        printBlankLine();
        System.out.println(FIXED_DISCOUNT_RATIO_GUIDE);
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
