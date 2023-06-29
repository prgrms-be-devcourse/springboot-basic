package org.programers.vouchermanagement.view;

import org.programers.vouchermanagement.member.domain.MemberStatus;

import java.util.Scanner;
import java.util.UUID;

public class InputView {

    private static Scanner SCANNER = new Scanner(System.in);

    private InputView() {}

    public static DomainType inputDomainType() {
        return DomainType.from(SCANNER.nextInt());
    }

    public static Command inputCommand() {
        return Command.from(SCANNER.nextInt());
    }

    public static UUID inputUUID() {
        return UUID.fromString(SCANNER.next());
    }

    public static MemberStatus inputMemberStatus() {
        return MemberStatus.valueOf(SCANNER.next());
    }

    public static DiscountPolicyType inputDiscountPolicy() {
         return DiscountPolicyType.from(SCANNER.nextInt());
    }

    public static int inputValueOfPolicy() {
        return SCANNER.nextInt();
    }

    public static WalletReadOption inputWalletReadOption() {
        return WalletReadOption.from(SCANNER.nextInt());
    }
}
