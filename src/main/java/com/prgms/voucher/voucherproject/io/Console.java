package com.prgms.voucher.voucherproject.io;

import com.prgms.voucher.voucherproject.domain.voucher.MenuType;
import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import com.prgms.voucher.voucherproject.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {
    private static final Scanner sc = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    public void printMessage(String msg, boolean lnCheck) {
        if (lnCheck) {
            System.out.println(msg);
            return;
        }

        System.out.print(msg);
    }

    public void printVoucherInfo(Voucher voucher) {
        String message = MessageFormat.format("|UUID:{0} | VoucherType: {1} | percent:{2}|",
                voucher.getId(), voucher.getVoucherType().toString(), voucher.getDiscount());
        System.out.println(message);
    }

    public Long inputDiscountAmount(VoucherType voucherType) {
        switch (voucherType) {
            case FIXED -> this.printMessage(Constant.CREATE_FIXED_VOUCHER, true);
            case PERCENT -> this.printMessage(Constant.CREATE_PERCENT_VOUCHER, true);
        }
        Integer selectedNum = this.getNumber();

        if (selectedNum == null) return null;

        Long discount = Long.valueOf(selectedNum);

        if (discount == null) return null;

        return discount;
    }

    public MenuType inputMenu() {
        this.printMessage(Constant.CONSOLE_MENU, true);
        String menuType = sc.nextLine();

        try {
            return MenuType.getSelectedMenuType(menuType);
        } catch (InputMismatchException e) {
            logger.error("MenuType InputMismatchException -> {}", menuType);
            this.printMessage(Constant.WRONG_COMMAND, true);
            return null;
        }
    }

    public VoucherType inputVoucherType() {
        this.printMessage(Constant.CONSOLE_VOUCHER_MENU, false);
        Integer selectedNum = getNumber();

        if (selectedNum == null) return null;

        try {
            return VoucherType.getSelectedVoucherType(selectedNum);
        } catch (Exception e) {
            logger.error("VoucherType InputMismatchException -> {}", selectedNum);
            this.printMessage(e.getLocalizedMessage(), true);
            return null;
        }
    }

    private Integer getNumber() {
        int selectedNum = Integer.MIN_VALUE;
        try {
            selectedNum = sc.nextInt();
        } catch (InputMismatchException e) {
            logger.error("VoucherType InputMismatchException -> {}", selectedNum);
            return null;
        }
        sc.nextLine();
        return selectedNum;
    }
}
