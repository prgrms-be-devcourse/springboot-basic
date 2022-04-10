package com.example.voucher_manager.io;

import com.example.voucher_manager.domain.voucher.Voucher;
import com.example.voucher_manager.domain.voucher.VoucherType;

import java.util.Optional;

public class Console {

    private final Input input;
    private final Output output;
    private final InputValidator inputValidator;
    private final CommandOperator commandOperator;

    public Console(Input input, Output output, InputValidator inputValidator, CommandOperator commandOperator) {
        this.input = input;
        this.output = output;
        this.inputValidator = inputValidator;
        this.commandOperator = commandOperator;
    }

    public CommandType inputCommand() {
        printMenu();
        String userInput = input.input(" >> ");
        CommandType command = inputValidator.validateCommandType(userInput);

        switch (command) {
            case CREATE -> inputVoucherType();
            case LIST -> printVoucherList();
            case EXIT -> shutdownConsole();
            case BLACKLIST -> printBlackList();
            case ERROR -> output.printError();
        }

        return command;
    }

    private void inputVoucherType() {
        printVoucherType();
        String userInput = input.input(" >> ");
        VoucherType voucherType = inputValidator.validateVoucherType(userInput);

        if (voucherType.equals(VoucherType.ERROR)){
            output.printError();
            return;
        }

        Optional<Long> discountNumber = inputDiscountInformation(voucherType);

        if (discountNumber.isEmpty()){
            output.printError();
            return;
        }

        Voucher voucher = commandOperator.create(voucherType, discountNumber.get());
        output.print(voucher.toString());
    }

    private Optional<Long> inputDiscountInformation(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> printFixedVoucherRange();
            case PERCENT -> printPercentVoucherRange();
        }

        String userInput = input.input(" >> ");
        if (!inputValidator.isInteger(userInput)){
            return Optional.empty();
        }

        Long discountNumber = input.parseLong(userInput);
        boolean validDiscountNumber = switch (voucherType) {
            case FIXED -> inputValidator.isPositiveNumber(discountNumber);
            case PERCENT -> inputValidator.isCorrectRangeOfPercent(discountNumber);
            default -> throw new IllegalStateException("Unexpected value: " + voucherType);
        };

        if (!validDiscountNumber) {
            return Optional.empty();
        }

        return Optional.of(discountNumber);
    }

    private void printMenu() {
        output.print("""
               === Voucher Program ===
               Type **exit** to exit the program.
               Type **create** to create a new voucher.
               Type **list** to list all vouchers.
               Type **blacklist** to list all blacklist Customers.
                """);
    }

    private void printVoucherType() {
        output.print("""
                === Select Voucher Type ===
                Type **fixed** to quantitative discount the price.
                Type **percent** to proportionally discount the price.
                """);
    }

    private void printFixedVoucherRange() {
        output.print("""
                === Input Discount Amount ===
                Enter Positive Number (number > 0)
                """);
    }

    private void printPercentVoucherRange() {
        output.print("""
                === Input Discount Percent ===
                Enter a value between 0 and 100 (0 <= number <= 100)
                """);
    }

    private void printBlackList() {
        output.printItems(commandOperator.getBlacklist());
    }

    private void printVoucherList() {
        output.printItems(commandOperator.getVoucherList());
    }

    private void shutdownConsole() {
        output.exit();
    }
}