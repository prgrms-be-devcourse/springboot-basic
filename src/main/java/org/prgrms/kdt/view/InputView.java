package org.prgrms.kdt.view;

import org.prgrms.kdt.exception.ErrorMessage;
import org.prgrms.kdt.exception.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {
    private final static Scanner scanner = new Scanner(System.in);
    private final static int INPUT_SIZE = 2;
    private final static String SPLIT_CODE = " ";

    public static void initVoucherMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program. [exit]");
        System.out.println("Type create to create a new voucher. [create]");
        System.out.println("Type list to list all vouchers. [all]");
    }

    public static void initMemberMessage() {
        System.out.println("==== Show Black List ====");
        System.out.println("Type exit to exit the program. [exit]");
        System.out.println("Type read_all to read the list. [read_all]");
    }

    public static String input() {
        return scanner.nextLine();
    }

    public static void explainCreateVoucher() {
        System.out.println("=== Explain To Create Voucher ===");
        System.out.println("Check Your Type and Value");
        System.out.println("Fixed 1000");
        System.out.println("or");
        System.out.println("Percent 10");
    }

    public static void closeScanner() {
        scanner.close();
    }

    public static List<String> typeAndValue(String userInputMessage) {
        List<String> typeAndValue = Arrays.asList(userInputMessage.split(SPLIT_CODE));
        if (typeAndValue.size() != INPUT_SIZE) {
            throw new InvalidArgumentException(ErrorMessage.NOT_CORRECT_INPUT_MESSAGE);
        }
        return typeAndValue;
    }

}
