package org.prgrms.springbootbasic.view;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.service.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleView {

    private final TextIO textIO = TextIoFactory.getTextIO();

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        consoleView.printMenu();
        Menu menu = consoleView.inputMenu();

        if (menu == Menu.CREATE) {
            VoucherType voucherType = consoleView.selectVoucherType();
            if (voucherType == VoucherType.FIXED) {
                long amount = consoleView.selectAmount();
                System.out.println("amount= " + amount);
            } else {
                int percent = consoleView.selectPercent();
                System.out.println("percent= " + percent);
            }
        }
        if (menu == Menu.LIST) {
            List<Voucher> vouchers = new ArrayList<>();
            vouchers.add(new FixedAmountVoucher(UUID.randomUUID(), 10L));
            vouchers.add(new PercentDiscountVoucher(UUID.randomUUID(), 10));

            consoleView.printList(vouchers);
        }
        if (menu == Menu.EXIT) {
            System.out.println(menu);
        }
    }

    public void printMenu() {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println("=== Voucher Program ===");
        terminal.print("Type ");
        terminal.executeWithPropertiesConfigurator(
            props -> props.setPromptBold(true),
            t -> t.print("exit"));
        terminal.println(" to exit the program.");

        terminal.print("Type ");
        terminal.executeWithPropertiesConfigurator(
            props -> props.setPromptBold(true),
            t -> t.print("create"));
        terminal.println(" to create a new voucher.");

        terminal.print("Type ");
        terminal.executeWithPropertiesConfigurator(
            props -> props.setPromptBold(true),
            t -> t.print("list"));
        terminal.println(" to list all vouchers.");

        terminal.println();
    }

    public Menu inputMenu() {
        Menu menu = textIO.newEnumInputReader(Menu.class)
            .read("select menu");
        textIO.getTextTerminal().println();
        return menu;
    }

    public VoucherType selectVoucherType() {
        VoucherType voucherType = textIO.newEnumInputReader(VoucherType.class)
            .read("select voucher type");
        textIO.getTextTerminal().println();
        return voucherType;
    }

    public long selectAmount() {
        long amount = textIO.newLongInputReader()
            .read("select amount");
        textIO.getTextTerminal().println();
        return amount;
    }

    public int selectPercent() {
        int percent = textIO.newIntInputReader()
            .read("select percent");
        textIO.getTextTerminal().println();
        return percent;
    }

    public void printList(List<Voucher> vouchers) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println("==Voucher List==");
        terminal.println();

        for (Voucher voucher : vouchers) {
            if (voucher.getClass() == FixedAmountVoucher.class) {
                var fixedAmountVoucher = (FixedAmountVoucher) voucher;
                terminal.println("voucherId= " + fixedAmountVoucher.getVoucherId() + ", amount= "
                    + fixedAmountVoucher.getAmount());
            }
            if (voucher.getClass() == PercentDiscountVoucher.class) {
                var percentDiscountVoucher = (PercentDiscountVoucher) voucher;
                terminal.println("voucherId= " + voucher.getVoucherId() + ", percent= "
                    + percentDiscountVoucher.getPercent());
            }
        }
        terminal.println();
    }

}
