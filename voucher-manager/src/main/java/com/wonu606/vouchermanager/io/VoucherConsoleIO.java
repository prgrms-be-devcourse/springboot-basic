package com.wonu606.vouchermanager.io;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.menu.VoucherMenu;
import com.wonu606.vouchermanager.service.voucher.VoucherType;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class VoucherConsoleIO extends AbstractConsoleIO {

    public VoucherConsoleIO(ConsoleInput input, ConsolePrinter printer) {
        super(input, printer);
    }

    @Override
    public VoucherMenu selectMenu() {
        displayMenu();
        String menuSelection = input.readString(VoucherMenu.getAllNames(), "Menu");
        return VoucherMenu.getTypeByName(menuSelection);
    }

    public String selectVoucherType() {
        displayVoucherTypes();
        return input.readString(VoucherType.getAllNames(), "Type");
    }

    public void displayVoucherList(List<Voucher> voucherList) {
        printer.displayMessage("=== 바우처 리스트 ===");
        voucherList.forEach(v -> printer.displayMessage(v.toString()));
    }

    protected void displayMenu() {
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
