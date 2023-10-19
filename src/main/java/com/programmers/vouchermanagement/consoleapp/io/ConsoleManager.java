package com.programmers.vouchermanagement.consoleapp.io;

import com.programmers.vouchermanagement.consoleapp.menu.CreateVoucherMenu;
import com.programmers.vouchermanagement.consoleapp.menu.Menu;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequestDTO;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.beryx.textio.TextIO;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.programmers.vouchermanagement.constant.Constant.LINE_SEPARATOR;

@Component
public class ConsoleManager {
    //messages
    private static final String MENU_SELECTION_INSTRUCTION = """
            === Voucher Program ===
            Type **exit** to exit the program.
            Type **create** to create a new voucher.
            Type **list** to list all vouchers.
            """;
    private static final String CREATE_SELECTION_INSTRUCTION = """
            Please select the type of voucher to create.
            Type **fixed** to create a fixed amount voucher.
            Type **percent** to create a percent discount voucher.
            """;
    private static final String VOUCHER_DISCOUNT_AMOUNT_INSTRUCTION =
            "Please type the amount/percent of discount of the voucher.%s".formatted(LINE_SEPARATOR);
    private static final String EXIT_MESSAGE =
            "System exits.";
    private static final String CREATE_SUCCESS_MESSAGE =
            "The voucher(ID: %s) is successfully created.";
    private static final String INCORRECT_INPUT_MESSAGE =
            """
                    Such input is incorrect.
                    Please input a correct command carefully.
                    """;
    private static final String INVALID_VOUCHER_TYPE_MESSAGE =
            "Voucher type should be either fixed amount or percent discount voucher.";
    //---

    private final TextIO textIO;
    private final Logger logger;

    public ConsoleManager(TextIO textIO, Logger logger) {
        this.textIO = textIO;
        this.logger = logger;
    }

    public Menu selectMenu() {
        String input = textIO.newStringInputReader()
                .read(MENU_SELECTION_INSTRUCTION);

        return Menu.findMenu(input);
    }

    public CreateVoucherRequestDTO instructCreate() {
        String createMenu = textIO.newStringInputReader()
                .read(CREATE_SELECTION_INSTRUCTION);
        CreateVoucherMenu createVoucherMenu = CreateVoucherMenu.findCreateMenu(createMenu)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE));

        long discountValue = textIO.newLongInputReader()
                .read(VOUCHER_DISCOUNT_AMOUNT_INSTRUCTION);
        return new CreateVoucherRequestDTO(createVoucherMenu, discountValue);
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
    }

    public void printException(RuntimeException e) {
        textIO.getTextTerminal().println(e.getMessage());
        logger.error(e.getMessage());
    }
}
