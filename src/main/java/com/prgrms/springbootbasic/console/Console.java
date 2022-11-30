package com.prgrms.springbootbasic.console;

import com.prgrms.springbootbasic.customer.domain.Customer;
import com.prgrms.springbootbasic.voucher.VoucherType;

import java.text.MessageFormat;
import java.util.List;

import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class Console {

    private static final String COMMAND_NOT_SUPPORTED = "Command not supported yet.";
    private static final String VOUCHER_NOT_SUPPORTED = "Voucher not supported yet.";
    private static final String VOUCHER_MENU = "=== Voucher Program ===\n" +
            "Type **exit** to exit the program.\n" +
            "Type **create** to create a new voucher.\n" +
            "Type **list** to list all vouchers.\n" +
            "Type **blacklist** to list blacklist.";
    private static final String CUSTOMER_MENU = "=== Customer Program ===\n" +
            "Type **create** to create new customer.\n" +
            "Type **find** to find customer.\n" +
            "Type **list** to create list customers.\n" +
            "Type **update** to update customer.\n" +
            "Type **delete** to delete customer.\n" +
            "Type **exit** to exit the program";
    private static final String TYPE_VOUCHER_MESSAGE = "Type 'fixedAmount' for fixed amount voucher, or type 'percent' for percent voucher";
    private static final String TYPE_FIXED_AMOUNT_MESSAGE = "Chose fixedAmount. Type fixed amount(1 ~ 10000). Amount must be an integer.";
    private static final String TYPE_PERCENT_MESSAGE = "Chose percent. Type percent amount(1 ~ 99(%)). Amount must be an integer.";
    private static final String CREATE_SUCCESS_MESSAGE = "New voucher created!";
    private static final String VOUCHER_LIST_MESSAGE = "Voucher Type = {0} / discount amount = {1}";
    private static final String VOUCHER_EMPTY_MESSAGE = "You don't have any voucher";
    private static final String BLACKED_USER_MESSAGE = "User id = {0} / name = {1}";
    private static final String BLACKLIST_EMPTY_MESSAGE = "There is no blacked user";

    private static final String CUSTOMER_CREATE_SUCCESS_MESSAGE = "New customer created!";
    private static final String CUSTOMER_NAME_MESSAGE = "Type customer name.";
    private static final String CUSTOMER_FIND_SUCCESS_MESSAGE = "Customer : {0} {1} {2}";
    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer {0} not found.";
    private static final String CUSTOMER_EMPTY_MESSAGE = "There is no customer";
    private static final String UPDATE_CUSTOMER_NAME_MESSAGE = "Type new name you want to update";

    private final Reader reader;
    private final Printer printer;

    public Console(Reader reader, Printer printer) {
        this.reader = reader;
        this.printer = printer;
    }

    public String getCommand() {
        return reader.read();
    }

    public String getInput() {
        return reader.read();
    }

    public void printCommendNotSupported() {
        printer.printMessage(COMMAND_NOT_SUPPORTED);
    }

    public void printVoucherMenu() {
        printer.printMessage(VOUCHER_MENU);
    }

    public void printChoosingVoucher() {
        printer.printMessage(TYPE_VOUCHER_MESSAGE);
    }

    public void printDiscountAmountMessage(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED_AMOUNT ->
                    printer.printMessage(MessageFormat.format(TYPE_FIXED_AMOUNT_MESSAGE, voucherType.getInputValue()));
            case PERCENT ->
                    printer.printMessage(MessageFormat.format(TYPE_PERCENT_MESSAGE, voucherType.getInputValue()));
            default -> printer.printMessage(VOUCHER_NOT_SUPPORTED);
        }
    }

    public void printExceptionMessage(String exceptionMessage) {
        printer.printMessage(exceptionMessage);
    }

    public void printCreateSuccessMessage() {
        printer.printMessage(CREATE_SUCCESS_MESSAGE);
    }

    public void printVoucherList(List<Voucher> vouchers) {
        if (vouchers.isEmpty()) {
            printer.printMessage(VOUCHER_EMPTY_MESSAGE);
        } else {
            vouchers
                    .forEach(voucher -> System.out.println(
                            MessageFormat.format(VOUCHER_LIST_MESSAGE, voucher.getClass().getSimpleName(), voucher.getDiscountAmount())));
        }
    }

    public void printBlackList(List<Customer> blacklist) {
        if (blacklist.isEmpty()) {
            printer.printMessage(BLACKLIST_EMPTY_MESSAGE);
        } else {
            blacklist
                    .forEach(user -> System.out.println(
                            MessageFormat.format(BLACKED_USER_MESSAGE, user.getId(), user.getName())));
        }
    }

    public void printCustomerName() {
        printer.printMessage(CUSTOMER_NAME_MESSAGE);
    }

    public void printCustomerMenu() {
        printer.printMessage(CUSTOMER_MENU);
    }

    public void printCustomerCreateSuccess() {
        printer.printMessage(CUSTOMER_CREATE_SUCCESS_MESSAGE);
    }

    public void printCustomerFindSuccess(Customer customer) {
        printer.printMessage(MessageFormat.format(CUSTOMER_FIND_SUCCESS_MESSAGE,
                customer.getId(),
                customer.getName(),
                customer.getCreatedAt()));
    }

    public void printCustomerNotFound(String name) {
        printer.printMessage(MessageFormat.format(CUSTOMER_NOT_FOUND_MESSAGE, name));
    }

    public void printCustomerList(List<Customer> customers) {
        if (customers.isEmpty()) {
            printer.printMessage(CUSTOMER_EMPTY_MESSAGE);
        } else {
            customers
                    .forEach(customer -> System.out.println(
                            MessageFormat.format(CUSTOMER_FIND_SUCCESS_MESSAGE, customer.getId(), customer.getName(), customer.getCreatedAt())));
        }
    }

    public void printUpdateCustomerNameMessage() {
        printer.printMessage(UPDATE_CUSTOMER_NAME_MESSAGE);
    }
}
