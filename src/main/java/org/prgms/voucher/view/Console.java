package org.prgms.voucher.view;

import java.util.List;
import java.util.Scanner;

import org.prgms.voucher.entity.Voucher;
import org.prgms.voucher.entity.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class Console implements InputView, OutputView {
    private static final String REQUEST_INPUT_COMMAND = "=== Voucher Program ===\nType **exit** to exit the program.\nType **create** to create a new voucher.\nType **list** to list all vouchers.";
    private static final String REQUEST_INPUT_VOUCHER_TYPE = "Select a voucher type\n 1:FixedAmountVoucher 2:PercentDiscountVoucher";
    private static final String REQUEST_INPUT_DISCOUNT_AMOUNT = "Input voucher discount amount : ";
    private static final String REQUEST_INPUT_DISCOUNT_PERCENTAGE = "Input voucher discount percentage : ";
    private static final String EMPTY_VOUCHERS = "Empty Vouchers";
    private static final String ERROR_INPUT_NUMBER_TYPE = "[ERROR] 정수만 입력가능합니다.";

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String inputMenu() {
        System.out.println(REQUEST_INPUT_COMMAND);
        return scanner.nextLine().trim().toLowerCase();
    }

    @Override
    public String inputVoucherCommand() {
        System.out.println(REQUEST_INPUT_VOUCHER_TYPE);
        return scanner.nextLine().trim().toLowerCase();
    }

    @Override
    public long inputDiscountValue(VoucherType voucherType) {
        String message = REQUEST_INPUT_DISCOUNT_PERCENTAGE;

        if (voucherType == VoucherType.FIXED_AMOUNT) {
            message = REQUEST_INPUT_DISCOUNT_AMOUNT;
        }

        System.out.println(message);
        return convertToLong(scanner.nextLine().trim());
    }

    private Long convertToLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_INPUT_NUMBER_TYPE);
        }
    }

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.println(voucher);
    }

    @Override
    public void printAllVoucher(List<Voucher> vouchers) {
        if (vouchers.isEmpty()) {
            System.out.println(EMPTY_VOUCHERS);
        }

        for (Voucher voucher : vouchers) {
            System.out.println(voucher);
        }
    }

    @Override
    public void printError(String message) {
        System.out.println(message);
    }
}
