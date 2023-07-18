package com.wonu606.vouchermanager.console.controllercable.customer.io;

import com.wonu606.vouchermanager.console.AbstractConsoleIo;
import com.wonu606.vouchermanager.console.ConsoleInput;
import com.wonu606.vouchermanager.console.ConsolePrinter;
import com.wonu606.vouchermanager.console.controllercable.customer.CustomerControllerMenu;
import com.wonu606.vouchermanager.controller.customer.response.CustomerResponse;
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

    public void displayCustomerList(List<CustomerResponse> customerResponses) {
        printer.displayMessage("=== 보유한 Customer 리스트 ===");
        customerResponses.forEach(response -> printer.displayMessage(response.toString()));
    }

    public void displayVoucherList(List<OwnedVoucherResponse> VoucherResponses) {
        printer.displayMessage("=== 보유한 바우처 리스트 ===");
        VoucherResponses.forEach(response -> printer.displayMessage(response.toString()));
    }
}
