package org.prgrms.springbootbasic.view;

import static org.prgrms.springbootbasic.controller.Menu.BLACKLIST;
import static org.prgrms.springbootbasic.controller.Menu.CREATE;
import static org.prgrms.springbootbasic.controller.Menu.EXIT;
import static org.prgrms.springbootbasic.controller.Menu.LIST;
import static org.prgrms.springbootbasic.view.ConstantString.AMOUNT;
import static org.prgrms.springbootbasic.view.ConstantString.PERCENT;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_AMOUNT;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_MENU;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_PERCENT;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_VOUCHER_TYPE;
import static org.prgrms.springbootbasic.view.ConstantString.TO_CREATE_A_NEW_VOUCHER;
import static org.prgrms.springbootbasic.view.ConstantString.TO_EXIT_THE_PROGRAM;
import static org.prgrms.springbootbasic.view.ConstantString.TO_LIST_ALL_CUSTOMER_BLACK_LIST;
import static org.prgrms.springbootbasic.view.ConstantString.TO_LIST_ALL_VOUCHERS;
import static org.prgrms.springbootbasic.view.ConstantString.TYPE;
import static org.prgrms.springbootbasic.view.ConstantString.VOUCHER_ID;
import static org.prgrms.springbootbasic.view.ConstantString.VOUCHER_LIST;
import static org.prgrms.springbootbasic.view.ConstantString.VOUCHER_PROGRAM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgrms.springbootbasic.VoucherType;
import org.prgrms.springbootbasic.controller.Menu;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ConsoleView {

    private static final String FILE_CUSTOMER_BLACKLIST_CSV = "file:customer_blacklist.csv";
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final File customerBlackList;

    public ConsoleView(ApplicationContext applicationContext) throws IOException {
        this.customerBlackList = applicationContext.getResource(FILE_CUSTOMER_BLACKLIST_CSV)
            .getFile();
    }

    public void printMenu() {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.println(VOUCHER_PROGRAM);
        printLine(terminal, EXIT.name(), TO_EXIT_THE_PROGRAM);
        printLine(terminal, CREATE.name(), TO_CREATE_A_NEW_VOUCHER);
        printLine(terminal, LIST.name(), TO_LIST_ALL_VOUCHERS);
        printLine(terminal, BLACKLIST.name(), TO_LIST_ALL_CUSTOMER_BLACK_LIST);
        terminal.println();
    }

    private void printLine(TextTerminal<?> terminal, String menu, String explain) {
        terminal.print(TYPE);
        terminal.executeWithPropertiesConfigurator(
            props -> props.setPromptBold(true),
            t -> t.print(menu));
        terminal.println(explain);
    }

    public Menu inputMenu() {
        Menu menu = textIO.newEnumInputReader(Menu.class)
            .read(SELECT_MENU);
        textIO.getTextTerminal().println();
        return menu;
    }

    public VoucherType selectVoucherType() {
        VoucherType voucherType = textIO.newEnumInputReader(VoucherType.class)
            .read(SELECT_VOUCHER_TYPE);
        textIO.getTextTerminal().println();
        return voucherType;
    }

    public long selectAmount() {
        long amount = textIO.newLongInputReader()
            .withMaxVal(FixedAmountVoucher.MIN_RANGE)
            .withMaxVal(FixedAmountVoucher.MAX_RANGE)
            .read(SELECT_AMOUNT);
        textIO.getTextTerminal().println();
        return amount;
    }

    public int selectPercent() {
        int percent = textIO.newIntInputReader()
            .withMaxVal(100)
            .read(SELECT_PERCENT);
        textIO.getTextTerminal().println();
        return percent;
    }

    public void printList(List<Voucher> vouchers) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println(VOUCHER_LIST);
        terminal.println();

        vouchers.forEach(voucher -> printVoucher(terminal, voucher));
        terminal.println();
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

    public void printCustomerBlackList() {
        TextTerminal<?> terminal = textIO.getTextTerminal();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(customerBlackList));
            String line = "";

            terminal.println("=== CUSTOMER_BLACK_LIST ===");
            while ((line = br.readLine()) != null) {
                terminal.println(line);
            }
            terminal.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
