package com.example.voucher.io;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.example.voucher.constant.ConstantStrings;
import com.example.voucher.domain.dto.VoucherDTO;
import com.example.voucher.constant.VoucherType;

public class Console {

    private static Scanner scanner = new Scanner(System.in);

    private Console() {

    }

    public static void printModeType() {
        System.out.println(ConstantStrings.MESSAGE_PRINT_MODE_SELECTION);
    }

    public static void printVoucherType() {
        System.out.println(ConstantStrings.MESSAGE_PRINT_TYPE_SELECTION);
    }

    public static void printDiscountAmount() {
        System.out.println(ConstantStrings.MESSAGE_PRINT_DISCOUNT_PRICE);
    }

    public static void printDiscountPercent() {
        System.out.println(ConstantStrings.MESSAGE_PRINT_DISCOUNT_PERCENT);
    }

    public static void printVoucherInfo(VoucherDTO voucherDTO) {
        VoucherType voucherType = voucherDTO.voucherType();

        switch (voucherType) {
            case FixedAmountDiscount -> System.out.println(
                String.format(ConstantStrings.FORMAT_PRINT_FIXED_AMOUNT_VOUCHER_INFO, voucherDTO.voucherType(),
                    voucherDTO.value()));
            case PercentDiscount -> System.out.println(
                String.format(ConstantStrings.FORMAT_PRINT_PERCENT_VOUCHER_INFO, voucherDTO.voucherType(),
                    voucherDTO.value()));
        }
    }

    public static void printError(String errorMsg) {
        System.out.println(errorMsg);
    }

    public static String readModeType() {
        return scanner.nextLine();
    }

    public static Integer readVoucherType() {
        try {
            Integer type = scanner.nextInt();
            return type;
        } catch (InputMismatchException e) {
            throw new InputMismatchException(ConstantStrings.MESSAGE_ERROR_INPUT_NUMBER);
        } finally {
            scanner.nextLine();
        }
    }

    public static long readDiscount() {
        try {
            Long discount = scanner.nextLong();
            return discount;
        } catch (InputMismatchException e) {
            throw new InputMismatchException(ConstantStrings.MESSAGE_ERROR_INPUT_NUMBER);
        } finally {
            scanner.nextLine();
        }
    }

}
