package com.programmers.voucher.global.io;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.request.VoucherCreateRequest;
import com.programmers.voucher.global.util.VoucherErrorMessages;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class TextIoConsole implements Console {
    private final TextIO textIO;

    public TextIoConsole(TextIO textIO) {
        this.textIO = textIO;
    }

    @Override
    public ConsoleCommandType inputInitialCommand() {
        String command = textIO.newStringInputReader()
                .read();

        return ConsoleCommandType.getValue(command);
    }

    @Override
    public void printCommandSet() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.println("=== Voucher Program ===");
        printCommand(ConsoleCommandType.EXIT, "to exit the program.");
        printCommand(ConsoleCommandType.CREATE, "to create a new voucher.");
        printCommand(ConsoleCommandType.LIST, "to list all vouchers.");
        printCommand(ConsoleCommandType.HELP, "to list command set.");
        printCommand(ConsoleCommandType.BLACKLIST, "to list blacklist.");
    }

    private void printCommand(ConsoleCommandType commandType, String behavior) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.print("Type");
        textTerminal.executeWithPropertiesConfigurator(
                props -> props.setPromptBold(true),
                t -> t.print(" " + commandType.getInput() + " ")
        );
        textTerminal.println(behavior);
    }

    @Override
    public VoucherCreateRequest inputVoucherCreateInfo() {
        VoucherType voucherType = inputVoucherType();
        switch (voucherType) {
            case FIXED_AMOUNT -> {
                long amount = inputFixedAmount();
                return new VoucherCreateRequest(voucherType, amount);
            }
            case PERCENT -> {
                long percent = inputPercentDiscount();
                return new VoucherCreateRequest(voucherType, percent);
            }
        }

        throw new IllegalStateException(VoucherErrorMessages.UNHANDLED_VOUCHER_TYPE);
    }

    private VoucherType inputVoucherType() {
        String rawVoucherType = textIO.newStringInputReader()
                .withValueChecker((val, itemName) -> voucherTypeValidateErrorMessages(val))
                .read("1. [fixed | percent]");

        return VoucherType.getValue(rawVoucherType);
    }

    private List<String> voucherTypeValidateErrorMessages(String val) {
        boolean invalidVoucherType = Arrays.stream(VoucherType.values())
                .map(VoucherType::getType)
                .noneMatch(rawType -> Objects.equals(rawType, val));

        List<String> messages = new ArrayList<>();
        if(invalidVoucherType) {
            messages.add(VoucherErrorMessages.INVALID_VOUCHER_TYPE + val);
        }
        return messages;
    }

    private long inputFixedAmount() {
        return textIO.newLongInputReader()
                .withValueChecker((val, itemName) -> fixedAmountValidateErrorMessages(val))
                .read("2. [amount]");
    }

    private List<String> fixedAmountValidateErrorMessages(Long val) {
        List<String> messages = new ArrayList<>();
        if(val <= 0) {
            messages.add(VoucherErrorMessages.INVALID_FIXED_AMOUNT + val);
        }
        return messages;
    }


    private long inputPercentDiscount() {
        return textIO.newLongInputReader()
                .withValueChecker((val, itemName) -> percentDiscountValidateErrorMessages(val))
                .read("2. [percent]");
    }

    private List<String> percentDiscountValidateErrorMessages(Long val) {
        List<String> messages = new ArrayList<>();
        if(val <= 0 || val >= 100) {
            messages.add(VoucherErrorMessages.INVALID_PERCENT_DISCOUNT + val);
        }
        return messages;
    }

    @Override
    public void print(String result) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println(result);
    }

    @Override
    public void exit() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.println("Bye Bye.");
    }

}
