package com.programmers.vouchermanagement.consoleapp.io;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.beryx.textio.TextIO;
import org.springframework.stereotype.Component;

import com.programmers.vouchermanagement.consoleapp.menu.CustomerMenu;
import com.programmers.vouchermanagement.consoleapp.menu.Menu;
import com.programmers.vouchermanagement.consoleapp.menu.VoucherMenu;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.customer.dto.UpdateCustomerRequest;
import com.programmers.vouchermanagement.util.Formatter;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.UpdateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherCustomerRequest;
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
            4. Update A Voucher
            5. Delete A Voucher
            6. Grant A Voucher to A Customer
            7. Search for A Owner of A Voucher
            """;
    private static final String CUSTOMER_MENU_SELECTION = """
            Please select the menu
            1. Create A New Customer
            2. List All Customers
            3. Search for A Customer
            4. Update Customer
            5. Delete Customer
            6. List All Customers in Blacklist
            7. Search Vouchers of A Customer
            8. Remove A Voucher from A Customer
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
    private static final String CUSTOMER_NAME_INPUT = "Please write the name of the customer to be saved." + LINE_SEPARATOR;
    private static final String ID_INPUT = "Please write the %s ID" + LINE_SEPARATOR;
    private static final String DELETE_SUCCESSFUL = "Item is successfully deleted.";
    private static final String CUSTOMER_TYPE_NAME_INPUT = """
            Write **normal** if you want to update this customer to exclude from the blacklist, or
            Write **black** if you want to update the customer to be in the blacklist.
            """;
    private static final String NO_OWNED_VOUCHER = "This customer has no voucher yet!";
    private static final String LISTING_OWNED_VOUCHERS_OF = "Listing vouchers that Customer %s has ...";
    private static final String GRANT_SUCCESSFUL = "Voucher (%s) is granted to Customer (%s).";
    private static final String VOUCHER_OWNER_BELOW = "The owner of Voucher %s is provided below:";

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
        String voucherType = read(VOUCHER_TYPE_INPUT);
        String discountValueInput = read(VOUCHER_DISCOUNT_VALUE_INSTRUCTION);
        long discountValue = Long.parseLong(discountValueInput);
        return new CreateVoucherRequest(discountValue, voucherType);
    }

    public UpdateVoucherRequest instructUpdateVoucher() {
        String voucherIdInput = read(ID_INPUT.formatted(CONTENT_VOUCHER));
        UUID voucherId = UUID.fromString(voucherIdInput);

        String discountValue = read(VOUCHER_DISCOUNT_VALUE_INSTRUCTION);

        String voucherTypeCode = read(VOUCHER_TYPE_INPUT);
        return new UpdateVoucherRequest(voucherId, Long.parseLong(discountValue), voucherTypeCode);
    }

    public String instructCreateCustomer() {
        return read(CUSTOMER_NAME_INPUT);
    }

    public UpdateCustomerRequest instructUpdateCustomer() {
        String customerIdInput = read(ID_INPUT.formatted(CONTENT_CUSTOMER));
        UUID customerId = UUID.fromString(customerIdInput);

        String name = read(CUSTOMER_NAME_INPUT);

        String customerTypeName = read(CUSTOMER_TYPE_NAME_INPUT);
        CustomerType customerType = CustomerType.findCustomerType(customerTypeName);
        return new UpdateCustomerRequest(customerId, name, customerType);
    }

    public UUID instructFindVoucher() {
        String voucherId = read(ID_INPUT.formatted(CONTENT_VOUCHER));
        return UUID.fromString(voucherId);
    }

    public UUID instructFindCustomer() {
        String customerId = read(ID_INPUT.formatted(CONTENT_CUSTOMER));
        return UUID.fromString(customerId);
    }

    public VoucherCustomerRequest instructRequestVoucherCustomer() {
        String voucherId = read(ID_INPUT.formatted(CONTENT_VOUCHER));
        String customerId = read(ID_INPUT.formatted(CONTENT_CUSTOMER));
        return new VoucherCustomerRequest(UUID.fromString(voucherId), UUID.fromString(customerId));
    }

    public void printSaveVoucherResult(VoucherResponse voucherResponse) {
        print(CREATE_SUCCESS_MESSAGE.formatted(CONTENT_VOUCHER, voucherResponse.voucherId()));
    }

    public void printSaveCustomerResult(CustomerResponse customerResponse) {
        print(CREATE_SUCCESS_MESSAGE.formatted(CONTENT_CUSTOMER, customerResponse.customerId()));
    }

    public void printReadAllVouchers(List<VoucherResponse> voucherResponses) {
        if (voucherResponses.isEmpty()) {
            print(Formatter.formatNoContent(CONTENT_VOUCHER));
        }

        voucherResponses.forEach(this::printFoundVoucher);
    }

    public void printReadAllCustomers(List<CustomerResponse> customerResponses) {
        if (customerResponses.isEmpty()) {
            print(Formatter.formatNoContent(CONTENT_CUSTOMER));
        }

        customerResponses.forEach(this::printFoundCustomer);
    }

    public void printReadBlacklist(List<CustomerResponse> customerResponses) {
        if (customerResponses.isEmpty()) {
            print(Formatter.formatNoContent(CONTENT_BLACKLIST));
        }
        customerResponses.forEach(this::printFoundCustomer);
    }

    public void printFoundVoucher(VoucherResponse voucherResponse) {
        print(Formatter.formatVoucher(voucherResponse));
    }

    public void printFoundCustomer(CustomerResponse customerResponse) {
        print(Formatter.formatCustomer(customerResponse));
    }

    public void printGranted(VoucherCustomerRequest request) {
        print(GRANT_SUCCESSFUL.formatted(request.voucherId(), request.customerId()));
    }

    public void printOwnedVouchers(UUID customerId, List<VoucherResponse> vouchers) {
        print(LISTING_OWNED_VOUCHERS_OF.formatted(customerId));
        if (vouchers.isEmpty()) {
            print(NO_OWNED_VOUCHER);
        }
        vouchers.forEach(this::printFoundVoucher);
    }

    public void printVoucherOwner(UUID voucherId, CustomerResponse customerResponse) {
        print(VOUCHER_OWNER_BELOW.formatted(voucherId));
        printFoundCustomer(customerResponse);
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
