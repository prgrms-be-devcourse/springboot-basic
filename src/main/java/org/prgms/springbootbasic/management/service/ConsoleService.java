package org.prgms.springbootbasic.management.service;

import org.prgms.springbootbasic.management.entity.FixedAmountVoucher;
import org.prgms.springbootbasic.management.entity.PercentAmountVoucher;
import org.prgms.springbootbasic.management.repository.MemoryVoucherRepository;
import org.prgms.springbootbasic.management.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleService {
    private final Scanner sc = new Scanner(System.in);
    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    public void run() {
        while (true) {
            initMessage();

            String command = sc.next();

            if (command.equals("exit")) {
                break;
            } else if (command.equals("create")) {
                createVoucher();
            } else if (command.equals("list")) {
                getVoucherList();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    private void initMessage() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
        System.out.println();
    }

    private void createVoucher() {
        System.out.println();
        System.out.println("***VoucherType VoucherName DiscountNum***");

        sc.nextLine();
        String[] voucher = sc.nextLine().split(" ");

        if (voucher.length != 3 || !voucher[2].matches("[0-9]"))
            throw new IllegalArgumentException();

        String voucherType = voucher[0];
        String voucherName = voucher[1];
        int discountNum = Integer.parseInt(voucher[2]);

        if (voucherType.equalsIgnoreCase("FixedAmountVoucher")) {
            if (discountNum < 0) throw new IllegalArgumentException();
            voucherRepository.save(
                    new FixedAmountVoucher(
                            UUID.randomUUID(), discountNum, voucherName
                    ));
        } else if (voucherType.equalsIgnoreCase("PercentDiscountVoucher")) {
            if (discountNum < 1 || discountNum > 100) throw new IllegalArgumentException();
            voucherRepository.save(
                    new PercentAmountVoucher(
                            UUID.randomUUID(), discountNum, voucherName
                    ));
        } else {
            throw new IllegalArgumentException();
        }
        System.out.println("Voucher Created");
        System.out.println();
    }

    private void getVoucherList() {
        System.out.println();
        System.out.println("=== Voucher List ===");
        voucherRepository.getAll()
                .forEach((k, v) -> System.out.println(MessageFormat
                        .format("{0} {1} {2}",
                                v.getVoucherId(),
                                v.getVoucherName(),
                                v.getDiscountNum()
                        )
                ));
        System.out.println();
    }
}
