package com.programmers.vouchermanagement.view;

import com.programmers.vouchermanagement.voucher.dto.VoucherDto;

import java.util.Scanner;
import java.util.stream.Collectors;

public class Console {

    private final static Scanner SCANNER = new Scanner(System.in);

    private static String inputString() {
        return SCANNER.nextLine();
    }

    public static String selectCommand() {
        System.out.println("""

                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """);
        return inputString();
    }

    public static String selectDiscountType() {
        System.out.println("""

                Choose a discount type.
                Type fix to select a fixed discount
                Type percent to select a fixed discount
                """);
        return inputString();
    }

    public static int inputDiscountAmount() {
        System.out.println("\nPlease enter the discount amount\n");
        return inputInt();
    }

    private static int inputInt() {
        int inputValue = 0;
        try {
            inputValue = Integer.parseInt(inputString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter only numbers.");
        }
        return inputValue;
    }

    public static void outputVouchers(VoucherDto.Response vouchers) {
        System.out.println("\nThis is a list of vouchers");
        System.out.println(vouchers.voucherName().stream()
                .map(voucher -> voucher + System.lineSeparator())
                .collect(Collectors.joining()));
    }

    public static void outputErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }
}
