package org.programers.vouchermanagement.view;

import java.util.Scanner;

public class InputView {

    private static Scanner SCANNER = new Scanner(System.in);

    private InputView() {}

    public static DomainType inputDomainType() {
        return DomainType.from(SCANNER.nextInt());
    }

    public static Command inputCommand() {
        return Command.from(SCANNER.nextInt());
    }

    public static DiscountPolicyType inputDiscountPolicy() {
         return DiscountPolicyType.from(SCANNER.nextInt());
    }

    public static int inputValueOfPolicy() {
        return SCANNER.nextInt();
    }
}
