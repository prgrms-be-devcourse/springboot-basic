package org.prgrms.springbootbasic.view;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

public class ConsoleView {

    private final TextIO textIO = TextIoFactory.getTextIO();

    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        consoleView.printMenu();
        consoleView.inputMenu();
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

    void inputMenu() {
        String menu = textIO.newStringInputReader()
            .withPossibleValues("exit", "create", "list")
            .read();
    }
}
