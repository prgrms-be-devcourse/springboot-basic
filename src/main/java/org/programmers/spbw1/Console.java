package org.programmers.spbw1;

import org.programmers.spbw1.io.Input;
import org.programmers.spbw1.io.Output;

public class Console implements Input, Output {
    @Override
    public String input() {
        return null;
    }

    @Override
    public void initSelect() {
        System.out.println("=== Voucher Program ===\n");
        System.out.println("Type **exit** to exit the program.\n");
        System.out.println("Type **create** to create a new voucher.\n");
        System.out.println("Type **list** to list all vouchers.");
    }
}
