package com.programmers.vouchermanagement.console;

import java.util.UUID;

import org.beryx.textio.TextIO;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.PercentVoucher;
import com.programmers.vouchermanagement.voucher.Voucher;

@Component
public class ConsoleManager {
    private static final String menuSelectionInstruction = """
            ===Voucher Program ===
            Type **exit** to exit the program.
            Type **create** to create a new voucher.
            Type **list** to list all vouchers.
            """;
    private static final String createSelectionInstruction = """
            Please select the type of voucher to create.
            Type **fixed** to create a fixed amount voucher.
            Type **percent** to create a percent discount voucher.
            """;

    private static final String voucherDiscountAmountDecision = "Please type the amount/percent of discount of the voucher.";

    private final TextIO textIO;

    public ConsoleManager(TextIO textIO) {
        this.textIO = textIO;
    }

    public String selectMenu() {
        return textIO.newStringInputReader()
                .read(menuSelectionInstruction);
    }

    public Voucher instructCreate() {
        String createMenu = textIO.newStringInputReader()
                .read(createSelectionInstruction);

        long discountAmount = textIO.newLongInputReader()
                .read(voucherDiscountAmountDecision);

        switch (createMenu) {
            case "fixed" -> {
                return new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
            }

            case "percent" -> {
                return new PercentVoucher(UUID.randomUUID(), discountAmount);
            }

            default ->
                    throw new IllegalArgumentException("Voucher type should be either fixed amount or percent discount voucher");
        }
    }

    public void printExit() {
        textIO.getTextTerminal().println("System exits.");
    }
}
