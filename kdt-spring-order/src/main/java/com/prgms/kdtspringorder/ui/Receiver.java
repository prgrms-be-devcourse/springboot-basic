package com.prgms.kdtspringorder.ui;

import java.util.Scanner;

import com.prgms.kdtspringorder.ui.message.InputMessage;

public class Receiver {
    public static final Scanner SCANNER = new Scanner(System.in);

    public String enterCommand() {
        System.out.println(InputMessage.ENTER_COMMAND);
        return SCANNER.nextLine();
    }

    public String enterVoucherType() {
        System.out.println(InputMessage.ENTER_VOUCHER_TYPE);
        return SCANNER.nextLine();
    }

    public long enterDiscountAmount() {
        System.out.println(InputMessage.ENTER_DISCOUNT_AMOUNT);
        long discountAmount = SCANNER.nextLong();
        SCANNER.nextLine();
        return discountAmount;
    }
}
