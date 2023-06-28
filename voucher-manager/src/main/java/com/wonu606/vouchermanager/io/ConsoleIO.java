package com.wonu606.vouchermanager.io;

import com.wonu606.vouchermanager.controller.VoucherMenu;
import com.wonu606.vouchermanager.domain.Voucher;
import com.wonu606.vouchermanager.service.VoucherType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ConsoleIO {

    ConsoleInput input = new ConsoleInput();
    ConsolePrinter printer = new ConsolePrinter();

    public String selectMenu() {
        displayMenu();
        return input.readString(VoucherMenu.getAllNames(), "Menu");
    }

    public String selectVoucherType() {
        displayVoucherTypes();
        return input.readString(VoucherType.getAllNames(), "Type");
    }

    public String readString(String description) {
        return input.readString(description);
    }

    public double readDouble(String description) {
        return input.readDouble(description);
    }

    public void displayVoucherList(List<Voucher> voucherList) {
        printer.displayMessage("=== 바우처 리스트 ===");
        voucherList.forEach(v -> printer.displayMessage(v.toString()));
    }

    public void displayMessage(String message) {
        printer.displayMessage(message);
    }

    public void terminal() {
        input.terminate();
        printer.terminate();
    }

    private void displayMenu() {
        String lineFormat = "Type %s to %s the program.\n";
        VoucherMenu.getAllNames().forEach(n ->
                printer.displayMessage(String.format(lineFormat, n, n)));
    }

    private void displayVoucherTypes() {
        printer.displayMessage("=== 바우처 타입 ===");
        VoucherType.getAllNames()
                .forEach(n -> printer.displayMessage("[" + n + "]"));
    }
}
