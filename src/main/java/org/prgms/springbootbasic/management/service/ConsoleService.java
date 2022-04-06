package org.prgms.springbootbasic.management.service;

import org.prgms.springbootbasic.management.entity.FixedAmountVoucher;
import org.prgms.springbootbasic.management.entity.PercentAmountVoucher;
import org.prgms.springbootbasic.management.repository.MemoryVoucherRepository;
import org.prgms.springbootbasic.management.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Scanner sc = new Scanner(System.in);
    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    public void run() {
        System.out.println("=== Voucher Program ===");
        label:
        while (true) {
            initMessage();
            System.out.print("Enter a command: ");
            String command = sc.next();

            switch (command) {
                case "exit":
                    break label;
                case "create":
                    createVoucher();
                    break;
                case "list":
                    getVoucherList();
                    break;
                default:
                    logger.error(MessageFormat.format
                            ("wrong input at input command -> {0}", command));
                    System.out.println();
                    break;
            }
        }
    }

    private void initMessage() {
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
        System.out.println();
    }

    private void createVoucher() {
        System.out.println();
        System.out.println("***VoucherType VoucherName DiscountNum***");
        System.out.println("Enter the voucher information.");

        System.out.print("VoucherType(1 <- FixedAmountVoucher, 2 <- PercentAmountVoucher): ");
        String voucherType = sc.next();

        if (voucherType.equals("1")) voucherType = "FixedAmountVoucher";
        else if (voucherType.equals("2")) voucherType = "PercentDiscountVoucher";
        else {
            logger.error(MessageFormat.format
                    ("wrong input at voucherType -> {0}", voucherType));
            System.out.println();
            return;
        }
        System.out.println();

        System.out.print("VoucherName: ");
        String voucherName = sc.next();

        if (voucherName.length() == 0) {
            logger.error("empty input at voucherName");
            System.out.println();
            return;
        }
        System.out.println();

        System.out.print("DiscountNum: ");
        String temp = sc.next();
        if (!temp.matches("[0-9]")) {
            logger.error(MessageFormat.format
                    ("wrong input at discountNum -> {0}", temp));
            System.out.println();
            return;
        }

        int discountNum = Integer.parseInt(temp);
        System.out.println();

        if (voucherType.equalsIgnoreCase("FixedAmountVoucher")) {
            if (discountNum < 1) {
                logger.error(MessageFormat.format
                        ("wrong input at discountNum -> {0}", discountNum));
                System.out.println();
                return;
            }
            voucherRepository.save(
                    new FixedAmountVoucher(
                            UUID.randomUUID(), discountNum, voucherName
                    ));
        } else {
            if (discountNum < 1 || discountNum > 100) {
                logger.error(MessageFormat.format
                        ("wrong input at discountNum -> {0}", discountNum));
                System.out.println();
                return;
            }
            voucherRepository.save(
                    new PercentAmountVoucher(
                            UUID.randomUUID(), discountNum, voucherName
                    ));
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
