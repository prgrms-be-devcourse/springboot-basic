package com.programmers;

import com.programmers.domain.Menu;
import com.programmers.domain.VoucherType;
import com.programmers.io.Console;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final Console console = new Console();

    public void run() {
        boolean activated = true;

        while (activated) {
            console.printMenu();
            String command = console.readInput();
            Menu menu = Menu.findMenu(command);

            switch (menu) {
                case EXIT -> activated = false;
                case CREATE -> createVoucher();
                default -> throw new IllegalArgumentException();
            }
        }
    }

    private void createVoucher() {
        console.printVoucherType();
        VoucherType voucherType = getVoucherType();

        console.printDiscountValueInput();
        Long discountValue = changeNumber(console.readInput());

        console.printVoucherNameInput();
        String voucherName = console.readInput();

        System.out.println("voucherType : " + voucherType.toString() + "  discountValue : " + discountValue + "  voucherName : " + voucherName);
    }

    public VoucherType getVoucherType() {
        String voucherTypeInput = console.readVoucherName();

        if (isNumeric(voucherTypeInput)) {
            return VoucherType.findVoucherTypeByNumber(voucherTypeInput);
        }

        return VoucherType.findVoucherTypeByName(voucherTypeInput);
    }

    public boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public Long changeNumber(String str) {
        try {
            Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        return Long.parseLong(str);
    }
}
