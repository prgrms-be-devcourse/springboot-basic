package org.prgms.management.io;

import org.prgms.management.entity.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Component
public class ConsoleOutput implements Output {
    @Override
    public void init() {
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
        System.out.println("Type **blacklist** to list all blacklist.\n");
    }

    @Override
    public void chooseVoucherType() {
        System.out.println("\n***VoucherType VoucherName DiscountNum***");
        System.out.println("Enter the voucher information.");
    }

    @Override
    public void voucherCreateSuccess() {
        System.out.println("\nVoucher created\n");
    }

    @Override
    public void voucherCreateFail() {
        System.out.println("\nFail create voucher\n");
    }

    @Override
    public void voucherList(Map<UUID, Voucher> voucherList) {
        System.out.println("\n=== Voucher list ===");
        if (!voucherList.isEmpty()) {
            voucherList.forEach((k, v) -> System.out.println(MessageFormat
                    .format("{0} {1} {2} {3}",
                            v.getVoucherId(),
                            v.getVoucherType(),
                            v.getVoucherName(),
                            v.getDiscountNum()
                    )
            ));
        }
        System.out.println();
    }

    @Override
    public void blackList(Map<UUID, String> blackList) {
        System.out.println("\n=== Blacklist ===");
        if (!blackList.isEmpty()) {
            blackList.forEach((k, v) -> System.out.println(MessageFormat
                    .format("{0} {1}", k, v)));
        }
        System.out.println();
    }
}
