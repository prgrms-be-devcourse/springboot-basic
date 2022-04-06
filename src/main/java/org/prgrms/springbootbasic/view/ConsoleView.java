package org.prgrms.springbootbasic.view;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgrms.springbootbasic.service.VoucherType;

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

}
