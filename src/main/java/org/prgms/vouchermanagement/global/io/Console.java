package org.prgms.vouchermanagement.global.io;

import org.prgms.vouchermanagement.customer.domain.entity.Customer;
import org.prgms.vouchermanagement.voucher.validator.VoucherInputValidator;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
        System.out.println("Type 'black' to list customer blacklist.");
        System.out.println("Type 'customers' to list all customers.");
        System.out.print("Input: ");
    }

    public void printSelectVoucherType() {
        System.out.println();
        System.out.println("1. FixedAmountVoucher");
        System.out.println("2. PercentDiscountVoucher");
        System.out.print("Type 1 or 2: ");
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
        System.out.print("Type 1 or 2: ");
    }

    public void printVoucherList(Map<UUID, Voucher> voucherList, VoucherType listVoucherType) {
        if (voucherList.isEmpty()) {
            System.out.println("조회할 Voucher가 없습니다!!!");
            return;
        }

        if (listVoucherType == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE) {
            System.out.println("=== Fixed Amount Voucher List ===");
            voucherList.forEach((k, v) -> {
                    if (v.getVoucherType() == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE)
                        System.out.println(MessageFormat.format("VoucherId: {0}, Discount: {1}", k, v.returnDiscount()));
                }
            );
        } else if (listVoucherType == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE) {
            System.out.println("=== Percent Discount Voucher List ===");
            voucherList.forEach((k, v) -> {
                    if (v.getVoucherType() == VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE)
                        System.out.println(MessageFormat.format("VoucherId: {0}, Discount: {1}", k, v.returnDiscount()));
                }
            );
        }
    }

    public void printCustomerBlackList(String path) throws IOException {
        System.out.println("=== Customer Blacklist ===");
        List<List<String>> records = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            records.add(Arrays.asList(values));
        }

        for (List<String> eachRecord: records) {
            System.out.println(eachRecord.toString());
        }
        br.close();
    }

    public void printCustomerList(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("조회할 Customer가 없습니다.");
            return;
        }

        System.out.println("=== Customer List ===");
        customers.forEach(customer ->
                System.out.println(MessageFormat.format("CustomerId: {0}, Name: {1}, Email: {2}",
                        customer.getCustomerId(), customer.getName(), customer.getEmail()))
        );
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
        printGetFixedVoucherAmount();
        String amount = scanner.nextLine();
        validator.checkFixedAmount(amount);

        return Long.parseLong(amount);
    }

    public long getPercentDiscount() {
        printGetPercentDiscount();
        String percent = scanner.nextLine();
        validator.checkPercent(percent);

        return Long.parseLong(percent);
    }
}
