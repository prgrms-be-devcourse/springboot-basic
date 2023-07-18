package com.wonu606.vouchermanager.console.controllercable.customer.io;

import com.wonu606.vouchermanager.console.AbstractConsoleIo;
import com.wonu606.vouchermanager.console.ConsoleInput;
import com.wonu606.vouchermanager.console.ConsolePrinter;
import com.wonu606.vouchermanager.console.controllercable.customer.CustomerControllerMenu;
import com.wonu606.vouchermanager.controller.customer.response.CustomerGetOwnedVouchersResponse;
import com.wonu606.vouchermanager.controller.customer.response.CustomerGetResponse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerConsoleIo extends AbstractConsoleIo {

    public CustomerConsoleIo(ConsoleInput input, ConsolePrinter printer) {
        super(input, printer);
    }

    @Override
    public CustomerControllerMenu selectMenu() {
        displayMenu();
        String menuSelection = input.readString(CustomerControllerMenu.getAllNames(), "Menu");
        return CustomerControllerMenu.getTypeByName(menuSelection);
    }

    protected void displayMenu() {
        String lineFormat = "Type %s to %s the program.\n";
        CustomerControllerMenu.getAllNames().forEach(n ->
                printer.displayMessage(String.format(lineFormat, n, n)));
    }

    public void displayCustomerList(CustomerGetResponse response) {
        printer.displayMessage("=== 보유한 Customer 리스트 ===");
        List<String> emails = response.getEmails();
        emails.forEach(printer::displayMessage);
    }

    public void displayVoucherList(CustomerGetOwnedVouchersResponse response) {
        printer.displayMessage("=== 보유한 바우처 리스트 ===");
        List<String> voucherIds = response.getVoucherIds();
        voucherIds.forEach(printer::displayMessage);
    }
}
