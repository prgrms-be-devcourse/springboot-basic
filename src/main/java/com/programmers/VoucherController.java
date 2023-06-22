package com.programmers;

import com.programmers.domain.*;
import com.programmers.io.Console;
import com.programmers.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class VoucherController {

    private final Console console = new Console();
    private final VoucherService voucherService = new VoucherService();

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

    public VoucherType getVoucherType() {
        String voucherTypeInput = console.readVoucherName();

        if (isNumeric(voucherTypeInput)) {
            return VoucherType.findVoucherTypeByNumber(voucherTypeInput);
        }

        return VoucherType.findVoucherTypeByName(voucherTypeInput);
    }

    public Voucher createVoucher() {
        Voucher voucher = makeVoucher();
        voucherService.save(voucher);
        console.printVoucherCreated();

        return voucher;
    }

    public Voucher makeVoucher() {
        console.printVoucherType();
        VoucherType voucherType = getVoucherType();

        console.printDiscountValueInput();
        Long discountValue = changeNumber(console.readInput());

        console.printVoucherNameInput();
        String voucherName = console.readInput();

        if (voucherType == VoucherType.FixedAmountVoucher) {
            return new FixedAmountVoucher(UUID.randomUUID(), voucherName, discountValue);
        }

        return new PercentDiscountVoucher(UUID.randomUUID(), voucherName, discountValue);
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
