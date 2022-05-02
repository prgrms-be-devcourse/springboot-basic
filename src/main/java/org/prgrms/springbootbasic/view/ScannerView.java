package org.prgrms.springbootbasic.view;

import static org.prgrms.springbootbasic.controller.Menu.ASSIGNVOUCHER;
import static org.prgrms.springbootbasic.controller.Menu.BLACKLIST;
import static org.prgrms.springbootbasic.controller.Menu.CREATE;
import static org.prgrms.springbootbasic.controller.Menu.CREATECUSTOMER;
import static org.prgrms.springbootbasic.controller.Menu.DELETECUSTOMERVOUCHER;
import static org.prgrms.springbootbasic.controller.Menu.EXIT;
import static org.prgrms.springbootbasic.controller.Menu.LIST;
import static org.prgrms.springbootbasic.controller.Menu.LISTCUSTOMER;
import static org.prgrms.springbootbasic.controller.Menu.LISTCUSTOMERHAVINGSEPCIFICVOUCHERTYPE;
import static org.prgrms.springbootbasic.controller.Menu.LISTCUSTOMERVOUCHER;
import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.INVALID_MENU_EXP_MSG;
import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.INVALID_UUID_FORMAT_EXP_MSG;
import static org.prgrms.springbootbasic.exception.ServiceExceptionMessage.INVALID_VOUCHER_TYPE_EXP_MSG;
import static org.prgrms.springbootbasic.view.ViewText.AMOUNT;
import static org.prgrms.springbootbasic.view.ViewText.CUSTOMER_LIST;
import static org.prgrms.springbootbasic.view.ViewText.CUSTOMER_LIST_PATTERN;
import static org.prgrms.springbootbasic.view.ViewText.CUSTOMER_S_VOUCHER_LIST;
import static org.prgrms.springbootbasic.view.ViewText.FAIL;
import static org.prgrms.springbootbasic.view.ViewText.PERCENT;
import static org.prgrms.springbootbasic.view.ViewText.SELECT_AMOUNT;
import static org.prgrms.springbootbasic.view.ViewText.SELECT_CUSTOMER_EMAIL;
import static org.prgrms.springbootbasic.view.ViewText.SELECT_CUSTOMER_ID;
import static org.prgrms.springbootbasic.view.ViewText.SELECT_CUSTOMER_NAME;
import static org.prgrms.springbootbasic.view.ViewText.SELECT_PERCENT;
import static org.prgrms.springbootbasic.view.ViewText.SELECT_VOUCHER_ID;
import static org.prgrms.springbootbasic.view.ViewText.SELECT_VOUCHER_TYPE;
import static org.prgrms.springbootbasic.view.ViewText.TO_ASSIGN_VOUCHER_TO_CUSTOMER;
import static org.prgrms.springbootbasic.view.ViewText.TO_CREATE_A_NEW_CUSTOMER;
import static org.prgrms.springbootbasic.view.ViewText.TO_CREATE_A_NEW_VOUCHER;
import static org.prgrms.springbootbasic.view.ViewText.TO_DELETE_CUSTOMER_S_VOUCHER;
import static org.prgrms.springbootbasic.view.ViewText.TO_EXIT_THE_PROGRAM;
import static org.prgrms.springbootbasic.view.ViewText.TO_LIST_ALL_CUSTOMERS;
import static org.prgrms.springbootbasic.view.ViewText.TO_LIST_ALL_CUSTOMER_BLACK_LIST;
import static org.prgrms.springbootbasic.view.ViewText.TO_LIST_ALL_VOUCHERS;
import static org.prgrms.springbootbasic.view.ViewText.TO_LIST_CUSTOMERS_HAVING_SPECIFIC_VOUCHER_TYPE;
import static org.prgrms.springbootbasic.view.ViewText.TO_LIST_CUSTOMER_S_VOUCHER;
import static org.prgrms.springbootbasic.view.ViewText.TYPE;
import static org.prgrms.springbootbasic.view.ViewText.VOUCHER_ID;
import static org.prgrms.springbootbasic.view.ViewText.VOUCHER_LIST;
import static org.prgrms.springbootbasic.view.ViewText.VOUCHER_PROGRAM;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.Menu;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Customer;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.exception.InvalidMenuInput;
import org.prgrms.springbootbasic.exception.InvalidVoucherTypeInput;
import org.prgrms.springbootbasic.exception.InvalidateUUIDFormat;

public class ScannerView implements View {

    private final Scanner scanner;
    private final CustomerBlackList blackList;

    public ScannerView(Scanner scanner, CustomerBlackList blackList) {
        this.scanner = scanner;
        this.blackList = blackList;
    }

    @Override
    public void printMenu() {
        System.out.println(VOUCHER_PROGRAM.getText());
        printLine(EXIT.name(), TO_EXIT_THE_PROGRAM.getText());
        printLine(CREATE.name(), TO_CREATE_A_NEW_VOUCHER.getText());
        printLine(LIST.name(), TO_LIST_ALL_VOUCHERS.getText());
        printLine(BLACKLIST.name(), TO_LIST_ALL_CUSTOMER_BLACK_LIST.getText());
        printLine(CREATECUSTOMER.name(), TO_CREATE_A_NEW_CUSTOMER.getText());
        printLine(LISTCUSTOMER.name(), TO_LIST_ALL_CUSTOMERS.getText());
        printLine(ASSIGNVOUCHER.name(), TO_ASSIGN_VOUCHER_TO_CUSTOMER.getText());
        printLine(LISTCUSTOMERVOUCHER.name(), TO_LIST_CUSTOMER_S_VOUCHER.getText());
        printLine(DELETECUSTOMERVOUCHER.name(), TO_DELETE_CUSTOMER_S_VOUCHER.getText());
        printLine(LISTCUSTOMERHAVINGSEPCIFICVOUCHERTYPE.name(),
            TO_LIST_CUSTOMERS_HAVING_SPECIFIC_VOUCHER_TYPE.getText());
        System.out.println();
    }

    @Override
    public Menu inputMenu() {
        try {
            return Menu.valueOf(scanner.next().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidMenuInput(INVALID_MENU_EXP_MSG);
        }
    }

    @Override
    public VoucherType selectVoucherType() {
        System.out.println(SELECT_VOUCHER_TYPE.getText());

        try {
            return VoucherType.valueOf(scanner.next().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidVoucherTypeInput(INVALID_VOUCHER_TYPE_EXP_MSG);
        }
    }

    @Override
    public int selectAmount() {
        System.out.println(SELECT_AMOUNT.getText());
        return scanner.nextInt();
    }

    @Override
    public int selectPercent() {
        System.out.println(SELECT_PERCENT.getText());
        return scanner.nextInt();
    }

    @Override
    public void printList(List<Voucher> vouchers) {
        System.out.println(VOUCHER_LIST.getText());

        vouchers.forEach(this::printVoucher);
        System.out.println();
    }

    @Override
    public void printCustomerBlackList() {
        blackList.printCustomerBlackList();
    }

    @Override
    public String selectName() {
        System.out.println(SELECT_CUSTOMER_NAME.getText());
        return scanner.next();
    }

    @Override
    public String selectEmail() {
        System.out.println(SELECT_CUSTOMER_EMAIL.getText());
        return scanner.next();
    }

    @Override
    public void printAllCustomers(List<Customer> customers) {
        System.out.println(CUSTOMER_LIST);

        customers.forEach(customer -> System.out.println(
            MessageFormat.format(CUSTOMER_LIST_PATTERN.getText(),
                customer.getCustomerId(),
                customer.getName().getName(),
                customer.getEmail().getEmail())));
        System.out.println();
    }

    @Override
    public void printError(String message) {
        System.out.println(FAIL.getText());
        System.out.println(message);
        System.out.println();
    }

    @Override
    public UUID selectVoucherId() {
        System.out.println(SELECT_VOUCHER_ID.getText());
        try {
            return UUID.fromString(scanner.next());
        } catch (IllegalArgumentException exception) {
            throw new InvalidateUUIDFormat(INVALID_UUID_FORMAT_EXP_MSG);
        }
    }

    @Override
    public UUID selectCustomerId() {
        System.out.println(SELECT_CUSTOMER_ID.getText());
        try {
            return UUID.fromString(scanner.next());
        } catch (IllegalArgumentException exception) {
            throw new InvalidateUUIDFormat(INVALID_UUID_FORMAT_EXP_MSG);
        }
    }

    @Override
    public void printCustomerVouchers(List<Voucher> customerVoucher) {
        System.out.println(CUSTOMER_S_VOUCHER_LIST.getText());

        customerVoucher.forEach(this::printVoucher);
        System.out.println();
    }

    private void printLine(String menu, String explain) {
        System.out.print(TYPE.getText());
        System.out.print(menu);
        System.out.println(explain);
    }

    private void printVoucher(Voucher voucher) {
        System.out.print(VOUCHER_ID.getText() + voucher.getVoucherId());

        if (voucher.isFixed()) {
            System.out.println(AMOUNT.getText() + ((FixedAmountVoucher) voucher).getAmount());
        }
        if (voucher.isPercent()) {
            System.out.println(PERCENT.getText() + ((PercentDiscountVoucher) voucher).getPercent());
        }
    }
}
