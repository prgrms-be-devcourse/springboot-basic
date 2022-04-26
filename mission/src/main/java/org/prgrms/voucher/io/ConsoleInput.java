package org.prgrms.voucher.io;

import org.prgrms.voucher.models.VoucherType;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements Input {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public String getString() {

        return sc.nextLine();
    }

    @Override
    public VoucherType getVoucherType() {

        String userInput = sc.nextLine();

        return VoucherType.findByUserInput(userInput);
    }

    @Override
    public long getDiscountValue() {

        String userInput = sc.nextLine();

        try {
            return Long.parseLong(userInput);
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException();
        }
    }
}