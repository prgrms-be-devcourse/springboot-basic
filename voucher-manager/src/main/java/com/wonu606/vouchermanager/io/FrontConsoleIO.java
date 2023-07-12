package com.wonu606.vouchermanager.io;

import com.wonu606.vouchermanager.menu.ConsoleMenu;
import org.springframework.stereotype.Component;

@Component
public class FrontConsoleIO extends AbstractConsoleIO {

    public FrontConsoleIO(ConsoleInput input, ConsolePrinter printer) {
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
