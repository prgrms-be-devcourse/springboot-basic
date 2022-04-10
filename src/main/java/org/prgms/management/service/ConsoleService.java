package org.prgms.management.service;

import org.prgms.management.entity.Voucher;
import org.prgms.management.repository.VoucherMemoryRepository;
import org.prgms.management.repository.VoucherRepository;
import org.prgms.management.entity.FixedAmountVoucher;
import org.prgms.management.entity.PercentAmountVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.UUID;

@Service
public class ConsoleService implements VoucherService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Scanner sc = new Scanner(System.in);
    private final VoucherRepository voucherRepository = new VoucherMemoryRepository();

    @Override
    public void run() {
        System.out.println("=== Voucher Program ===");
        label:
        while (true) {
            System.out.println("Type **exit** to exit the program.");
            System.out.println("Type **create** to create a new voucher.");
            System.out.println("Type **list** to list all vouchers.\n");
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
                    getErrorMsg(MessageFormat.format
                            ("wrong input at input command -> {0}\n", command));
                    break;
            }
        }
    }

    @Override
    public void createVoucher() {
        System.out.println("\n***VoucherType VoucherName DiscountNum***");
        System.out.println("Enter the voucher information.");

        System.out.print("VoucherType(1 <- FixedAmountVoucher, 2 <- PercentAmountVoucher): ");
        String voucherType = sc.next();

        if (voucherType.equals("1")) voucherType = "FixedAmountVoucher";
        else if (voucherType.equals("2")) voucherType = "PercentDiscountVoucher";
        else {
            getErrorMsg(MessageFormat.format
                    ("wrong input at voucherType -> {0}", voucherType));
            return;
        }

        System.out.print("\nVoucherName: ");
        String voucherName = sc.next();

        if (voucherName.length() == 0) {
            getErrorMsg("empty input at voucherName");
            return;
        }
        System.out.println();

        System.out.print("DiscountNum: ");
        String temp = sc.next();

        if (!temp.matches("[0-9]")) {
            getErrorMsg(MessageFormat.format
                    ("Wrong input at discountNum -> {0}", temp));
            return;
        }

        int discountNum = Integer.parseInt(temp);
        System.out.println();

        Voucher voucher;

        if (voucherType.equalsIgnoreCase("FixedAmountVoucher")) {
            if (discountNum < 1) {
                getErrorMsg(MessageFormat.format
                        ("wrong input at discountNum -> {0}", discountNum));
                return;
            }
            voucher = new FixedAmountVoucher(
                    UUID.randomUUID(), discountNum, voucherName);
        } else {
            if (discountNum < 1 || discountNum > 100) {
                getErrorMsg(MessageFormat.format
                        ("Wrong input at discountNum -> {0}", discountNum));
                return;
            }
            voucher = new PercentAmountVoucher(
                    UUID.randomUUID(), discountNum, voucherName);
        }

        if (voucherRepository.save(voucher)) {
            System.out.println("Voucher created");
        } else {
            System.out.println("Fail create voucher");
        }
        System.out.println();
    }

    @Override
    public void getVoucherList() {
        System.out.println();
        System.out.println("=== Voucher list ===");
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

    private void getErrorMsg(String errorMsg) {
        logger.error(errorMsg);
        System.out.println(errorMsg);
    }
}
