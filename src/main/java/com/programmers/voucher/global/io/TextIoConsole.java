package com.programmers.voucher.global.io;

import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.global.util.CommonErrorMessages;
import com.programmers.voucher.global.util.ConsoleMessages;
import com.programmers.voucher.global.util.VoucherErrorMessages;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.programmers.voucher.domain.voucher.util.VoucherDiscountRange.*;
import static com.programmers.voucher.global.util.ConsoleMessages.*;
import static com.programmers.voucher.global.util.VoucherErrorMessages.*;

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

        textTerminal.println(ConsoleMessages.VOUCHER_PROGRAM);
        printCommand(ConsoleCommandType.EXIT, EXIT_BEHAVIOR);
        printCommand(ConsoleCommandType.CREATE, CREATE_BEHAVIOR);
        printCommand(ConsoleCommandType.LIST, LIST_BEHAVIOR);
        printCommand(ConsoleCommandType.HELP, HELP_BEHAVIOR);
        printCommand(ConsoleCommandType.BLACKLIST, BLACKLIST_BEHAVIOR);
    }

    private void printCommand(ConsoleCommandType commandType, String behavior) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textTerminal.print(INPUT);
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
                .read(VOUCHER_TYPES);

        return VoucherType.getValue(rawVoucherType);
    }

    private List<String> voucherTypeValidateErrorMessages(String val) {
        boolean invalidVoucherType = Arrays.stream(VoucherType.values())
                .map(VoucherType::getType)
                .noneMatch(rawType -> Objects.equals(rawType, val));

        List<String> messages = new ArrayList<>();
        if (invalidVoucherType) {
            String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_VOUCHER_TYPE, val);
            messages.add(errorMessage);
        }
        return messages;
    }

    private long inputFixedAmount() {
        return textIO.newLongInputReader()
                .withValueChecker((val, itemName) -> fixedAmountValidateErrorMessages(val))
                .read(AMOUNT);
    }

    private List<String> fixedAmountValidateErrorMessages(Long val) {
        List<String> messages = new ArrayList<>();
        if (val <= FIXED_AMOUNT_MIN) {
            String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_FIXED_AMOUNT, val);
            messages.add(errorMessage);
        }
        return messages;
    }


    private long inputPercentDiscount() {
        return textIO.newLongInputReader()
                .withValueChecker((val, itemName) -> percentDiscountValidateErrorMessages(val))
                .read(PERCENT);
    }

    private List<String> percentDiscountValidateErrorMessages(Long val) {
        List<String> messages = new ArrayList<>();
        if (val <= PERCENT_DISCOUNT_MIN || val >= PERCENT_DISCOUNT_MAX) {
            String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_PERCENT_DISCOUNT, val);
            messages.add(errorMessage);
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
        textTerminal.println(EXIT_CONSOLE);
    }

}
