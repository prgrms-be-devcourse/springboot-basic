package com.wonu606.vouchermanager.console.cableadapter;

import com.wonu606.vouchermanager.console.AbstractConsoleIo;
import com.wonu606.vouchermanager.console.ConsoleInput;
import com.wonu606.vouchermanager.console.ConsolePrinter;
import org.springframework.stereotype.Component;

@Component
public class FrontConsoleIo extends AbstractConsoleIo {

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
