package com.programmers.vouchermanagement.consoleapp.io;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.beryx.textio.TextIO;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.consoleapp.menu.CustomerMenu;
import com.programmers.vouchermanagement.consoleapp.menu.Menu;
import com.programmers.vouchermanagement.consoleapp.menu.VoucherMenu;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.util.Formatter;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;

@Component
public class ConsoleManager {
    //messages
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String MENU_SELECTION_INSTRUCTION = """
            === Voucher Program ===
            0. Exit The program
            1. Voucher Menu
            2. Customer Menu
            """;
    private static final String VOUCHER_MENU_SELECTION = """
            Please select the menu.
            1. Create A New Voucher
            2. List All Vouchers
            3. Search for A Voucher
            4. Update Voucher
            5. Delete Voucher
            """;
    private static final String CUSTOMER_MENU_SELECTION = """
            Please select the menu
            1. Create A New Customer
            2. List All Customers
            3. Search for A Customer
            4. Update Customer
            5. Delete Customer
            6. List All Customers in Blacklist
            """;
    private static final String VOUCHER_TYPE_INPUT = """
            Please select the type of voucher.
            1. Fixed Amount Voucher
            2. Percent discount voucher.
            """;
    private static final String VOUCHER_DISCOUNT_VALUE_INSTRUCTION =
            "Please type the amount/percent of discount of the voucher.%s".formatted(LINE_SEPARATOR);
    private static final String EXIT_MESSAGE = "System exits.";
    private static final String CREATE_SUCCESS_MESSAGE = "The voucher(ID: %s) is successfully saved.";
    private static final String INCORRECT_INPUT_MESSAGE = """
             Such input is incorrect.
             Please input a correct command carefully.""";
    private static final String CONTENT_VOUCHER = "voucher";
    private static final String CONTENT_BLACKLIST = "black customer";
    private static final String CUSTOMER_CREATE_INSTRUCTION = "Please write the name of the customer";
    private static final String VOUCHER_ID_INPUT = "Please write the voucher ID";
    private static final String DELETE_SUCCESSFUL = "Item is successfully deleted.";

    private final TextIO textIO;

    public ConsoleManager(TextIO textIO) {
        this.textIO = textIO;
    }

    public Menu selectMenu() {
        String input = textIO.newStringInputReader()
                .read(MENU_SELECTION_INSTRUCTION);

        return Menu.findMenu(input);
    }

    public VoucherMenu selectVoucherMenu() {
        String input = textIO.newStringInputReader()
                .read(VOUCHER_MENU_SELECTION);

        return VoucherMenu.findVoucherMenu(input);
    }

    public CustomerMenu selectCustomerMenu() {
        String input = textIO.newStringInputReader()
                .read(CUSTOMER_MENU_SELECTION);

        return CustomerMenu.findCustomerMenu(input);
    }

    public CreateVoucherRequest instructCreateVoucher() {
        String voucherTypeCode = textIO.newStringInputReader()
                .read(VOUCHER_TYPE_INPUT);
        VoucherType voucherType = VoucherType.findVoucherTypeByCode(voucherTypeCode);

        String discountValueInput = textIO.newStringInputReader()
                .read(VOUCHER_DISCOUNT_VALUE_INSTRUCTION);
        BigDecimal discountValue = new BigDecimal(discountValueInput);
        return new CreateVoucherRequest(discountValue, voucherType);
    }

    public UpdateVoucherRequest instructUpdateVoucher() {
        String voucherIdInput = textIO.newStringInputReader()
                .read(VOUCHER_ID_INPUT);
        UUID voucherId = UUID.fromString(voucherIdInput);

        String discountValueInput = textIO.newStringInputReader()
                .read(VOUCHER_DISCOUNT_VALUE_INSTRUCTION);
        BigDecimal discountValue = new BigDecimal(discountValueInput);

        String voucherTypeCode = textIO.newStringInputReader()
                .read(VOUCHER_TYPE_INPUT);
        VoucherType voucherType = VoucherType.findVoucherTypeByCode(voucherTypeCode);
        return new UpdateVoucherRequest(voucherId, discountValue, voucherType);
    }

    public String instructCreateCustomer() {
        return textIO.newStringInputReader()
                .read(CUSTOMER_CREATE_INSTRUCTION);
    }

    public UUID instructFindVoucher() {
        String voucherId = textIO.newStringInputReader()
                .read(VOUCHER_ID_INPUT);
        return UUID.fromString(voucherId);
    }

    public void printSaveVoucherResult(VoucherResponse voucherResponse) {
        textIO.getTextTerminal()
                .println(CREATE_SUCCESS_MESSAGE.formatted(voucherResponse.getVoucherId()));
    }

    public void printReadAllVouchers(List<VoucherResponse> voucherResponses) {
        if (voucherResponses.isEmpty()) {
            textIO.getTextTerminal()
                    .println(Formatter.formatNoContent(CONTENT_VOUCHER));
        }

        voucherResponses.forEach(this::printReadVoucher);
    }

    public void printReadBlacklist(List<CustomerResponse> customerResponses) {
        if (customerResponses.isEmpty()) {
            textIO.getTextTerminal()
                    .println(Formatter.formatNoContent(CONTENT_BLACKLIST));
        }

        customerResponses.forEach(customerResponse -> textIO.getTextTerminal()
                .println(Formatter.formatCustomer(customerResponse)));
    }

    public void printReadVoucher(VoucherResponse voucherResponse) {
        textIO.getTextTerminal()
                .println(Formatter.formatVoucher(voucherResponse));
    }

    public void printDeleteResult() {
        textIO.getTextTerminal().println(DELETE_SUCCESSFUL);
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
