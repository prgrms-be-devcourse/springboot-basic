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
import static org.prgrms.springbootbasic.view.ConstantString.AMOUNT;
import static org.prgrms.springbootbasic.view.ConstantString.CUSTOMER_BLACK_LIST;
import static org.prgrms.springbootbasic.view.ConstantString.CUSTOMER_LIST;
import static org.prgrms.springbootbasic.view.ConstantString.CUSTOMER_LIST_PATTERN;
import static org.prgrms.springbootbasic.view.ConstantString.CUSTOMER_S_VOUCHER_LIST;
import static org.prgrms.springbootbasic.view.ConstantString.FAIL;
import static org.prgrms.springbootbasic.view.ConstantString.PERCENT;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_AMOUNT;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_CUSTOMER_EMAIL;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_CUSTOMER_ID;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_CUSTOMER_NAME;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_MENU;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_PERCENT;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_VOUCHER_ID;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_VOUCHER_TYPE;
import static org.prgrms.springbootbasic.view.ConstantString.TO_ASSIGN_VOUCHER_TO_CUSTOMER;
import static org.prgrms.springbootbasic.view.ConstantString.TO_CREATE_A_NEW_CUSTOMER;
import static org.prgrms.springbootbasic.view.ConstantString.TO_CREATE_A_NEW_VOUCHER;
import static org.prgrms.springbootbasic.view.ConstantString.TO_DELETE_CUSTOMER_S_VOUCHER;
import static org.prgrms.springbootbasic.view.ConstantString.TO_EXIT_THE_PROGRAM;
import static org.prgrms.springbootbasic.view.ConstantString.TO_LIST_ALL_CUSTOMERS;
import static org.prgrms.springbootbasic.view.ConstantString.TO_LIST_ALL_CUSTOMER_BLACK_LIST;
import static org.prgrms.springbootbasic.view.ConstantString.TO_LIST_ALL_VOUCHERS;
import static org.prgrms.springbootbasic.view.ConstantString.TO_LIST_CUSTOMERS_HAVING_SPECIFIC_VOUCHER_TYPE;
import static org.prgrms.springbootbasic.view.ConstantString.TO_LIST_CUSTOMER_S_VOUCHER;
import static org.prgrms.springbootbasic.view.ConstantString.TYPE;
import static org.prgrms.springbootbasic.view.ConstantString.VOUCHER_ID;
import static org.prgrms.springbootbasic.view.ConstantString.VOUCHER_LIST;
import static org.prgrms.springbootbasic.view.ConstantString.VOUCHER_PROGRAM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgrms.springbootbasic.controller.Menu;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.Customer;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.prgrms.springbootbasic.exception.InvalidateUUIDFormat;
import org.springframework.context.ApplicationContext;

public class TextIoView implements View {

    private static final String FILE_CUSTOMER_BLACKLIST_CSV = "file:customer_blacklist.csv";
    private static final String RED = "red";
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final File customerBlackList;

    public TextIoView(ApplicationContext applicationContext) throws IOException {
        this.customerBlackList = applicationContext.getResource(FILE_CUSTOMER_BLACKLIST_CSV)
            .getFile();
    }

    @Override
    public void printMenu() {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.println(VOUCHER_PROGRAM);
        printLine(terminal, EXIT.name(), TO_EXIT_THE_PROGRAM);
        printLine(terminal, CREATE.name(), TO_CREATE_A_NEW_VOUCHER);
        printLine(terminal, LIST.name(), TO_LIST_ALL_VOUCHERS);
        printLine(terminal, BLACKLIST.name(), TO_LIST_ALL_CUSTOMER_BLACK_LIST);
        printLine(terminal, CREATECUSTOMER.name(), TO_CREATE_A_NEW_CUSTOMER);
        printLine(terminal, LISTCUSTOMER.name(), TO_LIST_ALL_CUSTOMERS);
        printLine(terminal, ASSIGNVOUCHER.name(), TO_ASSIGN_VOUCHER_TO_CUSTOMER);
        printLine(terminal, LISTCUSTOMERVOUCHER.name(), TO_LIST_CUSTOMER_S_VOUCHER);
        printLine(terminal, DELETECUSTOMERVOUCHER.name(), TO_DELETE_CUSTOMER_S_VOUCHER);
        printLine(terminal, LISTCUSTOMERHAVINGSEPCIFICVOUCHERTYPE.name(),
            TO_LIST_CUSTOMERS_HAVING_SPECIFIC_VOUCHER_TYPE);
        terminal.println();
    }

    @Override
    public Menu inputMenu() {
        Menu menu = textIO.newEnumInputReader(Menu.class)
            .read(SELECT_MENU);
        textIO.getTextTerminal().println();
        return menu;
    }

    @Override
    public VoucherType selectVoucherType() {
        VoucherType voucherType = textIO.newEnumInputReader(VoucherType.class)
            .read(SELECT_VOUCHER_TYPE);
        textIO.getTextTerminal().println();
        return voucherType;
    }

    @Override
    public int selectAmount() {
        int amount = textIO.newIntInputReader()
            .withMaxVal(FixedAmountVoucher.MIN_RANGE)
            .withMaxVal(FixedAmountVoucher.MAX_RANGE)
            .read(SELECT_AMOUNT);
        textIO.getTextTerminal().println();
        return amount;
    }

    @Override
    public int selectPercent() {
        int percent = textIO.newIntInputReader()
            .withMinVal(PercentDiscountVoucher.MIN_RANGE)
            .withMaxVal(PercentDiscountVoucher.MAX_RANGE)
            .read(SELECT_PERCENT);
        textIO.getTextTerminal().println();
        return percent;
    }

    @Override
    public void printList(List<Voucher> vouchers) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println(VOUCHER_LIST);
        terminal.println();

        vouchers.forEach(voucher -> printVoucher(terminal, voucher));
        terminal.println();
    }

    @Override
    public void printCustomerBlackList() {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(customerBlackList));
            String line = "";

            terminal.println(CUSTOMER_BLACK_LIST);
            while ((line = br.readLine()) != null) {
                terminal.println(line);
            }
            terminal.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String selectName() {
        return textIO.newStringInputReader()
            .read(SELECT_CUSTOMER_NAME);
    }

    @Override
    public String selectEmail() {
        return textIO.newStringInputReader()
            .read(SELECT_CUSTOMER_EMAIL);
    }

    @Override
    public void printAllCustomers(List<Customer> customers) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println(CUSTOMER_LIST);
        terminal.println();

        customers.forEach(customer -> terminal.println(
            MessageFormat.format(CUSTOMER_LIST_PATTERN,
                customer.getCustomerId(), customer.getName(), customer.getEmail())));
        terminal.println();
    }

    @Override
    public void printError(String message) {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.println(FAIL);
        terminal.executeWithPropertiesConfigurator(
            props -> {
                props.setPromptColor(RED);
                props.setPromptBold(true);
            },
            t -> t.print(message));
        terminal.println();
    }

    @Override
    public UUID selectVoucherId() {
        var voucherId = textIO.newStringInputReader()
            .read(SELECT_VOUCHER_ID);
        validateUUIDFormat(voucherId);
        return UUID.fromString(voucherId);
    }

    @Override
    public UUID selectCustomerId() {
        var customerId = textIO.newStringInputReader()
            .read(SELECT_CUSTOMER_ID);
        validateUUIDFormat(customerId);
        return UUID.fromString(customerId);
    }


    @Override
    public void printCustomerVouchers(List<Voucher> customerVoucher) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println(CUSTOMER_S_VOUCHER_LIST);
        terminal.println();

        customerVoucher.forEach(voucher -> printVoucher(terminal, voucher));
        terminal.println();
    }

    private void printLine(TextTerminal<?> terminal, String menu, String explain) {
        terminal.print(TYPE);
        terminal.executeWithPropertiesConfigurator(
            props -> props.setPromptBold(true),
            t -> t.print(menu));
        terminal.println(explain);
    }

    private void printVoucher(TextTerminal<?> terminal, Voucher voucher) {
        terminal.print(VOUCHER_ID + voucher.getVoucherId());

        if (voucher.getClass() == FixedAmountVoucher.class) {
            var fixedAmountVoucher = (FixedAmountVoucher) voucher;
            terminal.println(AMOUNT + fixedAmountVoucher.getAmount());
        }
        if (voucher.getClass() == PercentDiscountVoucher.class) {
            var percentDiscountVoucher = (PercentDiscountVoucher) voucher;
            terminal.println(PERCENT + percentDiscountVoucher.getPercent());
        }
    }

    private void validateUUIDFormat(String uuid) {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException exception) {
            throw new InvalidateUUIDFormat();
        }
    }
}
