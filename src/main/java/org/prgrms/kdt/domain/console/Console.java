package org.prgrms.kdt.domain.console;

import org.prgrms.kdt.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Scanner;

public class Console{

    private final static Scanner scanner = new Scanner(System.in);

    private Console() {

    }

    public static String inputCommand() {
        System.out.println("=== Voucher Program");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
        return scanner.next();
    }


    public static String inputVoucherType() {
        System.out.println("Type voucherType: fixedAmount or percentDiscount");
        return scanner.next();
    }


    public static long inputDiscount() {
        System.out.print("Enter discount value: ");
        return scanner.nextLong();
    }

    public static void printExit() {
        System.out.println("프로그램을 종료합니다.");
    }

    public static void printAllVouchers(List<Voucher> vouchers) {
        vouchers.forEach(System.out::println);
    }

    public static void printWrongCommandError() {
        System.out.println("유효하지 않은 명령어 입니다");
    }
}
