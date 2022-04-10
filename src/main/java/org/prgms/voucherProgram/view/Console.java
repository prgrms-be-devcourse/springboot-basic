package org.prgms.voucherProgram.view;

import java.util.List;
import java.util.Scanner;

import org.prgms.voucherProgram.entity.user.User;
import org.prgms.voucherProgram.entity.voucher.Voucher;
import org.prgms.voucherProgram.entity.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Console implements InputView, OutputView {
    private static final String PROMPT = "> ";
    private static final String REQUEST_INPUT_COMMAND = "=== Voucher Program ===\nType \"exit\" to exit the program.\nType \"create\" to create a new voucher.\nType \"list\" to list all vouchers.\nType \"blacklist\" to list all black user.";
    private static final String REQUEST_INPUT_VOUCHER_TYPE = "\nSelect a voucher type\nType \"1\" to create a new FixedAmountVoucher\nType \"2\" to create a new PercentDiscountVoucher";
    private static final String REQUEST_INPUT_DISCOUNT_AMOUNT = "\nInput voucher discount amount : ";
    private static final String REQUEST_INPUT_DISCOUNT_PERCENTAGE = "\nInput voucher discount percentage : ";
    private static final String EMPTY_VOUCHERS = "Empty Vouchers";
    private static final String EMPTY_USERS = "Empty Users";
    private static final String ERROR_INPUT_NUMBER_TYPE = "[ERROR] 정수만 입력가능합니다.";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String inputMenu() {
        System.out.println(REQUEST_INPUT_COMMAND);
        System.out.print(PROMPT);
        return scanner.nextLine().trim().toLowerCase();
    }

    @Override
    public String inputVoucherCommand() {
        System.out.println(REQUEST_INPUT_VOUCHER_TYPE);
        System.out.print(PROMPT);
        return scanner.nextLine().trim().toLowerCase();
    }

    @Override
    public long inputDiscountValue(VoucherType voucherType) {
        String message = REQUEST_INPUT_DISCOUNT_PERCENTAGE;

        if (voucherType == VoucherType.FIXED_AMOUNT) {
            message = REQUEST_INPUT_DISCOUNT_AMOUNT;
        }

        System.out.print(message);
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
        System.out.printf("%s\n%n", voucher);
    }

    @Override
    public void printVouchers(List<Voucher> vouchers) {
        if (vouchers.isEmpty()) {
            System.out.printf("\n%s%n\n", EMPTY_VOUCHERS);
            return;
        }

        System.out.println();
        for (Voucher voucher : vouchers) {
            System.out.println(voucher);
        }
        System.out.println();
    }

    @Override
    public void printUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.printf("\n%s%n\n", EMPTY_USERS);
            return;
        }

        System.out.println();
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println();
    }

    @Override
    public void printError(String message) {
        System.out.println("\n" + message + "\n");
    }
}
