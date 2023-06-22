package org.prgms.vouchermanagement.io;

import org.prgms.vouchermanagement.validator.VoucherInputValidator;
import org.prgms.vouchermanagement.voucher.Voucher;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@Component
public class Console {

    private final Scanner scanner;
    private final VoucherInputValidator validator;

    public Console(VoucherInputValidator validator) {
        this.validator = validator;
        scanner = new Scanner(System.in);
    }

    public void printCommandMenu() {
        System.out.println();
        System.out.println("=== Voucher Program ===");
        System.out.println("Type 'exit' to exit the program.");
        System.out.println("Type 'create' to create a new voucher.");
        System.out.println("Type 'list' to list all vouchers.");
        System.out.print("Input: ");
    }

    public void printSelectVoucherType() {
        System.out.println();
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("Select 1 or 2: ");
    }

    public void printGetFixedVoucherAmount() {
        System.out.print("FixedAmountVoucher의 Amount 값을 입력해주세요: ");
    }

    public void printGetPercentDiscount() {
        System.out.print("PercentDiscountVoucher의 Percent 값을 입력해주세요 (1 ~ 99): ");
    }

    public void printSavedFinished() {
        System.out.println("Voucher saved Successfully!!!");
    }

    public void printSelectVoucherListType() {
        System.out.println();
        System.out.println("조회할 Voucher 리스트의 Type을 입력하세요.");
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("Select 1 or 2: ");
    }

    public void printVoucherList(Optional<Map<UUID, Voucher>> voucherList, VoucherType listVoucherType) {
        if (voucherList.isEmpty()) {
            System.out.println("조회할 Voucher가 없습니다!!!");
            return;
        }

        switch (listVoucherType) {
            case FIXED_AMOUNT_VOUCHER_TYPE -> System.out.println("=== Fixed Amount Voucher List ===");
            case PERCENT_DISCOUNT_VOUCHER_TYPE -> System.out.println("=== Percent Discount Voucher List ===");
        }

        voucherList.get().forEach((k, v) -> {
            System.out.println(MessageFormat.format("VoucherId: {0}, Discount: {1}", k, v.returnDiscount()));
        });
    }

    public String getCommand() {
        return scanner.nextLine();
    }

    public int getVoucherTypeInput() {
        String input = scanner.nextLine();
        validator.checkVoucherTypeInput(input);

        return Integer.parseInt(input);
    }

    public long getFixedVoucherAmount() {
        String amount = scanner.nextLine();
        validator.checkFixedAmount(amount);

        return Long.parseLong(amount);
    }

    public long getPercentDiscount() {
        String percent = scanner.nextLine();
        validator.checkPercent(percent);

        return Long.parseLong(percent);
    }
}
