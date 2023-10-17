package com.programmers.vouchermanagement.console;

import com.programmers.vouchermanagement.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.PercentVoucher;
import com.programmers.vouchermanagement.voucher.Voucher;
import org.beryx.textio.TextIO;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ConsoleManager {
    //TODO: consider responsibility of console manager regarding to messages
    private static final String menuSelectionInstruction = """
            === Voucher Program ===
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
    private final Logger logger;

    public ConsoleManager(TextIO textIO, Logger logger) {
        this.textIO = textIO;
        this.logger = logger;
    }

    //TODO: validate input type is correct
    public String selectMenu() {
        return textIO.newStringInputReader()
                .read(menuSelectionInstruction);
    }

    public Voucher instructCreate() {
        String createMenu = textIO.newStringInputReader()
                .read(createSelectionInstruction);

        //TODO: refactor order of inputs obtaining
        long discountAmount = textIO.newLongInputReader()
                .read(voucherDiscountAmountDecision);

        // TODO: reconsider constructing models (Bean, factory method in DTO)
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

    public void printReadAll(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> textIO.getTextTerminal().println(voucher.toConsoleFormat()));
    }

    public void printExit() {
        textIO.getTextTerminal().println("System exits.");
    }

    public void printException(RuntimeException e) {
        logger.error(e.getMessage());
        System.out.println(e.getMessage());
    }
}
