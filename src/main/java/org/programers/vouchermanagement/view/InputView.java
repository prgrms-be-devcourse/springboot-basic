package org.programers.vouchermanagement.view;

import java.util.Scanner;

public class InputView {

    private static Scanner SCANNER = new Scanner(System.in);

    public static Command inputCommand() {
        return Command.from(SCANNER.next());
    }

    public static DiscountPolicyType inputDiscountPolicy() {
         return DiscountPolicyType.from(SCANNER.nextInt());
    }
}
