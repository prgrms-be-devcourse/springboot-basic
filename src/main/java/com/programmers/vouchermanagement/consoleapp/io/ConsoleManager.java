package com.programmers.vouchermanagement.consoleapp.io;

import java.math.BigDecimal;
import java.util.List;

import org.beryx.textio.TextIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.consoleapp.menu.Menu;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.util.Formatter;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;

@Component
public class ConsoleManager {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleManager.class);

    //messages
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String MENU_SELECTION_INSTRUCTION = """
            === Voucher Program ===
            Type **exit** to exit the program.
            Type **create** to create a new voucher.
            Type **list** to list all vouchers.
            Type **blacklist** to list all customers in blacklist.
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
    private static final String CONTENT_VOUCHER = "voucher";
    private static final String CONTENT_BLACKLIST = "black customer";

    private final TextIO textIO;

    public ConsoleManager(TextIO textIO) {
        this.textIO = textIO;
    }

    public Menu selectMenu() {
        String input = textIO.newStringInputReader()
                .read(MENU_SELECTION_INSTRUCTION);

        return Menu.findMenu(input);
    }

    public CreateVoucherRequest instructCreate() {
        String createMenu = textIO.newStringInputReader()
                .read(CREATE_SELECTION_INSTRUCTION);
        VoucherType voucherType = VoucherType.findVoucherType(createMenu)
                .orElseThrow(() -> {
                    logger.error(INVALID_VOUCHER_TYPE_MESSAGE);
                    return new IllegalArgumentException(INVALID_VOUCHER_TYPE_MESSAGE);
                });

        String discountValueInput = textIO.newStringInputReader()
                .read(VOUCHER_DISCOUNT_AMOUNT_INSTRUCTION);
        BigDecimal discountValue = new BigDecimal(discountValueInput);
        return new CreateVoucherRequest(discountValue, voucherType);
    }

    public void printCreateResult(VoucherResponse voucherResponse) {
        textIO.getTextTerminal()
                .println(CREATE_SUCCESS_MESSAGE.formatted(voucherResponse.getVoucherId()));
    }

    public void printReadAllVouchers(List<VoucherResponse> voucherResponses) {
        if (voucherResponses.isEmpty()) {
            textIO.getTextTerminal()
                    .println(Formatter.formatNoContent(CONTENT_VOUCHER));
        }

        voucherResponses.forEach(voucherResponse -> textIO.getTextTerminal()
                .println(Formatter.formatVoucher(voucherResponse)));
    }

    public void printReadBlacklist(List<CustomerResponse> customerResponses) {
        if (customerResponses.isEmpty()) {
            textIO.getTextTerminal()
                    .println(Formatter.formatNoContent(CONTENT_BLACKLIST));
        }

        customerResponses.forEach(customerResponse -> textIO.getTextTerminal()
                .println(Formatter.formatCustomer(customerResponse)));
    }

    public void printExit() {
        textIO.getTextTerminal().println(EXIT_MESSAGE);
    }

    public void printIncorrectMenu() {
        textIO.getTextTerminal().println(INCORRECT_INPUT_MESSAGE);
    }

    public void printException(RuntimeException e) {
        textIO.getTextTerminal().println(e.getMessage());
    }
}
