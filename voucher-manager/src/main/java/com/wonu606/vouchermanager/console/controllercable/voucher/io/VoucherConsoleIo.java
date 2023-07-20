package com.wonu606.vouchermanager.console.controllercable.voucher.io;

import com.wonu606.vouchermanager.console.AbstractConsoleIo;
import com.wonu606.vouchermanager.console.ConsoleInput;
import com.wonu606.vouchermanager.console.ConsolePrinter;
import com.wonu606.vouchermanager.console.controllercable.voucher.VoucherControllerMenu;
import org.springframework.stereotype.Component;

@Component
public class VoucherConsoleIo extends AbstractConsoleIo {

    public VoucherConsoleIo(ConsoleInput input, ConsolePrinter printer) {
        super(input, printer);
    }

    @Override
    public VoucherControllerMenu selectMenu() {
        displayMenu();
        String menuSelection = input.readString(VoucherControllerMenu.getAllNames(), "Menu");
        return VoucherControllerMenu.getTypeByName(menuSelection);
    }

    public String selectVoucherType() {
        displayVoucherTypes();
        return input.readString(VoucherConsoleType.getAllNames(), "Type");
    }

    public void displayVoucherList(String voucherList) {
        printer.displayMessage("=== 바우처 리스트 ===");
        printer.displayMessage(voucherList);
    }

    public void displayCustomerList(String response) {
        printer.displayMessage("=== 보유한 Customer 리스트 ===");
        printer.displayMessage(response);
    }

    protected void displayMenu() {
        String lineFormat = "Type %s to %s the program.\n";
        VoucherControllerMenu.getAllNames().forEach(n ->
                printer.displayMessage(String.format(lineFormat, n, n)));
    }

    private void displayVoucherTypes() {
        printer.displayMessage("=== 바우처 타입 ===");
        VoucherConsoleType.getAllNames()
                .forEach(n -> printer.displayMessage("[" + n + "]"));
    }
}
