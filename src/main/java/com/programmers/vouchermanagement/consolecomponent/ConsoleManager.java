package com.programmers.vouchermanagement.consolecomponent;

import com.programmers.vouchermanagement.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.voucher.PercentVoucher;
import com.programmers.vouchermanagement.voucher.Voucher;
import org.beryx.textio.TextIO;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.programmers.vouchermanagement.consolecomponent.Menu.findSelectedMenu;
import static com.programmers.vouchermanagement.constant.message.ExceptionMessage.INVALID_VOUCHER_TYPE_MESSAGE;
import static com.programmers.vouchermanagement.constant.message.Instruction.*;
import static com.programmers.vouchermanagement.constant.message.ResultMessage.*;

@Component
public class ConsoleManager {
    private final TextIO textIO;
    private final Logger logger;

    public ConsoleManager(TextIO textIO, Logger logger) {
        this.textIO = textIO;
        this.logger = logger;
    }

    //TODO: validate input type is correct
    public Menu selectMenu() {
        String input = textIO.newStringInputReader()
                .read(MENU_SELECTION_INSTRUCTION);

        return findSelectedMenu(input);
    }

    public Voucher instructCreate() {
        String createMenu = textIO.newStringInputReader()
                .read(CREATE_SELECTION_INSTRUCTION);

        //TODO: refactor order of inputs obtaining
        long discountAmount = textIO.newLongInputReader()
                .read(VOUCHER_DISCOUNT_AMOUNT_INSTRUCTION);

        // TODO: reconsider constructing models (Bean, factory method in DTO)
        switch (createMenu) {
            case "fixed" -> {
                return new FixedAmountVoucher(UUID.randomUUID(), discountAmount);
            }

            case "percent" -> {
                return new PercentVoucher(UUID.randomUUID(), discountAmount);
            }

            default -> throw new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE);
        }
    }

    public void printCreateResult(Voucher voucher) {
        textIO.getTextTerminal().println(CREATE_SUCCESS_MESSAGE.formatted(voucher.getVoucherId()));
    }

    public void printReadAll(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> textIO.getTextTerminal().println(voucher.toConsoleFormat()));
    }

    public void printExit() {
        textIO.getTextTerminal().println(EXIT_MESSAGE);
    }

    public void printIncorrectMenu() {
        textIO.getTextTerminal().println(INCORRECT_INPUT_MESSAGE);
        printExit();
    }

    //TODO: add exception handling method externally
    public void printException(RuntimeException e) {
        logger.error(e.getMessage());
        textIO.getTextTerminal().println(e.getMessage());
    }
}
