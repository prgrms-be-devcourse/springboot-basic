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
    private static final String CREATE_SUCCESS_MESSAGE = "The %s(ID: %s) is successfully saved.";
    private static final String INCORRECT_INPUT_MESSAGE = """
             Such input is incorrect.
             Please input a correct command carefully.""";
    private static final String CONTENT_VOUCHER = "voucher";
    private static final String CONTENT_CUSTOMER = "customer";
    private static final String CONTENT_BLACKLIST = "black customer";
    private static final String CUSTOMER_CREATE_INSTRUCTION = "Please write the name of the customer";
    private static final String ID_INPUT = "Please write the %s ID";
    private static final String DELETE_SUCCESSFUL = "Item is successfully deleted.";

    private final TextIO textIO;

    public ConsoleManager(TextIO textIO) {
        this.textIO = textIO;
    }

    public Menu selectMenu() {
        String input = read(MENU_SELECTION_INSTRUCTION);
        return Menu.findMenu(input);
    }

    public VoucherMenu selectVoucherMenu() {
        String input = read(VOUCHER_MENU_SELECTION);
        return VoucherMenu.findVoucherMenu(input);
    }

    public CustomerMenu selectCustomerMenu() {
        String input = read(CUSTOMER_MENU_SELECTION);
        return CustomerMenu.findCustomerMenu(input);
    }

    public CreateVoucherRequest instructCreateVoucher() {
        String voucherTypeCode = read(VOUCHER_TYPE_INPUT);
        VoucherType voucherType = VoucherType.findVoucherTypeByCode(voucherTypeCode);

        String discountValueInput = read(VOUCHER_DISCOUNT_VALUE_INSTRUCTION);
        BigDecimal discountValue = new BigDecimal(discountValueInput);
        return new CreateVoucherRequest(discountValue, voucherType);
    }

    public UpdateVoucherRequest instructUpdateVoucher() {
        String voucherIdInput = read(ID_INPUT);
        UUID voucherId = UUID.fromString(voucherIdInput);

        String discountValueInput = read(VOUCHER_DISCOUNT_VALUE_INSTRUCTION);
        BigDecimal discountValue = new BigDecimal(discountValueInput);

        String voucherTypeCode = read(VOUCHER_TYPE_INPUT);
        VoucherType voucherType = VoucherType.findVoucherTypeByCode(voucherTypeCode);
        return new UpdateVoucherRequest(voucherId, discountValue, voucherType);
    }

    public String instructCreateCustomer() {
        return read(CUSTOMER_CREATE_INSTRUCTION);
    }

    public UUID instructFindVoucher() {
        String voucherId = read(ID_INPUT.formatted(CONTENT_VOUCHER));
        return UUID.fromString(voucherId);
    }

    public UUID instructFindCustomer() {
        String customerId = read(ID_INPUT.formatted(CONTENT_CUSTOMER));
        return UUID.fromString(customerId);
    }

    public void printSaveVoucherResult(VoucherResponse voucherResponse) {
        print(CREATE_SUCCESS_MESSAGE.formatted(CONTENT_VOUCHER,voucherResponse.getVoucherId()));
    }

    public void printSaveCustomerResult(CustomerResponse customerResponse) {
        print(CREATE_SUCCESS_MESSAGE.formatted(CONTENT_CUSTOMER, customerResponse.customerId()));
    }

    public void printReadAllVouchers(List<VoucherResponse> voucherResponses) {
        if (voucherResponses.isEmpty()) {
            print(Formatter.formatNoContent(CONTENT_VOUCHER));
        }

        voucherResponses.forEach(this::printReadVoucher);
    }

    public void printReadAllCustomers(List<CustomerResponse> customerResponses) {
        if (customerResponses.isEmpty()) {
            print(Formatter.formatNoContent(CONTENT_CUSTOMER));
        }

        customerResponses.forEach(this::printReadCustomer);
    }

    public void printReadBlacklist(List<CustomerResponse> customerResponses) {
        if (customerResponses.isEmpty()) {
            print(Formatter.formatNoContent(CONTENT_BLACKLIST));
        }

        customerResponses.forEach(this::printReadCustomer);
    }

    public void printReadVoucher(VoucherResponse voucherResponse) {
        print(Formatter.formatVoucher(voucherResponse));
    }

    public void printReadCustomer(CustomerResponse customerResponse) {
        print(Formatter.formatCustomer(customerResponse));
    }

    public void printDeleteResult() {
        print(DELETE_SUCCESSFUL);
    }

    public void printExit() {
        print(EXIT_MESSAGE);
    }

    public void printIncorrectMenu() {
        print(INCORRECT_INPUT_MESSAGE);
    }

    public void printException(RuntimeException e) {
        print(e.getMessage());
    }

    private String read(String prompt) {
        return textIO.newStringInputReader()
                .read(prompt);
    }

    private void print(String message) {
        textIO.getTextTerminal()
                .println(message);
    }
}
