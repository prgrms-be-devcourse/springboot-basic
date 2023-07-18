package com.wonu606.vouchermanager.console.consoleio.concreate;

import com.wonu606.vouchermanager.console.consoleio.AbstractConsoleIO;
import com.wonu606.vouchermanager.console.consoleio.ConsoleInput;
import com.wonu606.vouchermanager.console.cableadapter.ConsoleMenu;
import com.wonu606.vouchermanager.console.consoleio.ConsolePrinter;
import org.springframework.stereotype.Component;

@Component
public class FrontConsoleIo extends AbstractConsoleIO {

    public FrontConsoleIo(ConsoleInput input, ConsolePrinter printer) {
        super(input, printer);
    }

    @Override
    public ConsoleMenu selectMenu() {
        displayMenu();
        String menuSelection = input.readString(ConsoleMenu.getAllNames(), "Menu");
        return ConsoleMenu.getTypeByName(menuSelection);
    }

    protected void displayMenu() {
        String lineFormat = "Type %s to %s the program.\n";
        ConsoleMenu.getAllNames().forEach(n ->
                printer.displayMessage(String.format(lineFormat, n, n)));
    }
}
