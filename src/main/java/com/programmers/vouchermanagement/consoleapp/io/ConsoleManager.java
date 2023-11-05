package com.programmers.vouchermanagement.consoleapp.io;

import com.programmers.vouchermanagement.consoleapp.menu.Menu;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherType;
import com.programmers.vouchermanagement.voucher.domain.vouchertype.VoucherTypeManager;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import org.beryx.textio.TextIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static com.programmers.vouchermanagement.util.Constant.LINE_SEPARATOR;

@Profile("console")
@Component
public class ConsoleManager {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleManager.class);
    //messages
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
    private static final String PERCENTAGE = " %";
    private static final String EMPTY = "";
    private static final String NO_CONTENT = "There is no %s stored yet!";
    //---

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
        VoucherType voucherType = VoucherTypeManager.get(createMenu);

        String discountValueStr = textIO.newStringInputReader()
                .read(VOUCHER_DISCOUNT_AMOUNT_INSTRUCTION);
        return new CreateVoucherRequest(voucherType.getName(), Long.parseLong(discountValueStr));
    }

    public void printCreateResult(VoucherResponse voucher) {
        textIO.getTextTerminal().println(CREATE_SUCCESS_MESSAGE.formatted(voucher.id()));
    }

    public void printReadAllVouchers(List<VoucherResponse> vouchers) {
        if (vouchers.isEmpty()) {
            textIO.getTextTerminal().println(NO_CONTENT.formatted("voucher"));
        }
        vouchers.forEach(voucher -> textIO.getTextTerminal().println(formatVoucherDTO(voucher)));
    }

    public void printReadBlacklist(List<CustomerResponse> customers) {
        if (customers.isEmpty()) {
            textIO.getTextTerminal().println(NO_CONTENT.formatted("black customer"));
        }
        customers.forEach(customer -> textIO.getTextTerminal().println(formatCustomer(customer)));
    }

    private String formatCustomer(CustomerResponse customer) {
        return """
                Customer ID : %s
                Customer Name : %s
                -------------------------"""
                .formatted(customer.id(), customer.name());
    }

    private String formatVoucherDTO(VoucherResponse voucher) {
        return """
                Voucher ID : %s
                Voucher Type : %s Discount Voucher
                Discount Amount : %s
                -------------------------"""
                .formatted(voucher.id(),
                        voucher.typeName(),
                        voucher.discountValue() +
                                (Objects.equals(voucher.typeName(), "PERCENT") ? PERCENTAGE : EMPTY));
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
