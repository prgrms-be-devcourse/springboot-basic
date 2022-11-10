package prgms.vouchermanagementapp.io;

import prgms.vouchermanagementapp.voucher.VoucherType;
import prgms.vouchermanagementapp.voucher.model.Voucher;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Writer {

    private static final String DOT = ".";
    private static final String BLANK = " ";
    private static final String PROMPT_SIGNATURE = "> ";

    private static final String COMMAND_GUIDE =
            "Type exit to exit the program."
                    + System.lineSeparator()
                    + "Type create to create a new voucher."
                    + System.lineSeparator()
                    + "Type list to list all vouchers.";

    private static final String EXIT = "Terminating Application...";
    private static final String VOUCHER_GUIDE = "Enter index of voucher type you want to create.";
    private static final String FIXED_AMOUNT_GUIDE = "Enter fixed amount you want to get a discount.";
    private static final String FIXED_DISCOUNT_RATIO_GUIDE = "Enter fixed ratio you want to get a discount.";
    private static final String NO_VOUCHER_EXISTS = "There is no voucher. please create voucher first";

    public void print(List<String> list) {
        list.forEach(System.out::println);
    }

    public void printVouchers(List<Voucher> list) {
        if (list.isEmpty()) {
            System.out.println(NO_VOUCHER_EXISTS);
            return;
        }

        AtomicInteger index = new AtomicInteger();
        list.forEach((voucher) -> {
            String prompt = index.incrementAndGet() + BLANK + voucher.getClass().toString();
            System.out.println(prompt);
        });
    }

    public void printCommandGuide() {
        printBlankLine();
        System.out.println(COMMAND_GUIDE);
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
        System.out.println(EXIT);
    }

    private void printPromptSignature() {
        System.out.print(PROMPT_SIGNATURE);
    }

    private void printBlankLine() {
        System.out.println();
    }

    public void printException(RuntimeException e) {
        System.out.println(e.getMessage());
    }
}
