package com.wonu606.vouchermanager.io;

import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.menu.CustomerMenu;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerConsoleIO extends AbstractConsoleIO {

    public CustomerConsoleIO(ConsoleInput input, ConsolePrinter printer) {
        super(input, printer);
    }

    @Override
    public CustomerMenu selectMenu() {
        displayMenu();
        String menuSelection = input.readString(CustomerMenu.getAllNames(), "Menu");
        return CustomerMenu.getTypeByName(menuSelection);
    }

    protected void displayMenu() {
        String lineFormat = "Type %s to %s the program.\n";
        CustomerMenu.getAllNames().forEach(n ->
                printer.displayMessage(String.format(lineFormat, n, n)));
    }

    public void displayCustomerList(List<Customer> customerList) {
        printer.displayMessage("=== 보유한 Customer 리스트 ===");
        customerList.forEach(c -> printer.displayMessage(c.toString()));
    }

    public void displayVoucherList(List<Voucher> voucherList) {
        printer.displayMessage("=== 보유한 바우처 리스트 ===");
        voucherList.forEach(v -> printer.displayMessage(v.toString()));
    }
}
