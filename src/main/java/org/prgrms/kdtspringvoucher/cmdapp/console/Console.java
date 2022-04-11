package org.prgrms.kdtspringvoucher.cmdapp.console;

import org.prgrms.kdtspringvoucher.cmdapp.ServiceType;
import org.prgrms.kdtspringvoucher.voucher.service.FixedAmountVoucher;
import org.prgrms.kdtspringvoucher.voucher.service.PercentDiscountVoucher;
import org.prgrms.kdtspringvoucher.voucher.service.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public ServiceType getServiceType() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher.");
        System.out.println("Type list to list all vouchers.");
        System.out.println("Type blacklist to list all black customer list.");
        System.out.print("Enter a command: ");

        String command = sc.next();

        return switch (command.toLowerCase()) {
            case "list" -> ServiceType.LIST;
            case "create" -> ServiceType.CREATE;
            case "blacklist" -> ServiceType.BLACKLIST;
            default -> ServiceType.EXIT;
        };
    }

    public Voucher getVoucher() {
        System.out.println(">>>>>>>>>>>>>>Create Voucher<<<<<<<<<<<<<<<<");
        System.out.print("Select Voucher Type (1:Fixed Voucher or 2:Percentage Voucher): ");
        String voucherType = sc.next();

        if (voucherType.equals("1")) {
            System.out.print("Input Fixed Voucher Discount Amount: ");
            long amount = sc.nextLong();

            return new FixedAmountVoucher(amount);

        } else if (voucherType.equals("2")) {
            System.out.print("Input Percentage Voucher Discount Percentage: ");
            int percent = sc.nextInt();

            return new PercentDiscountVoucher(percent);

        } else {
            return null;
        }
    }

    @Override
    public void printList(List list) {
        for (Object value : list) {
            System.out.println(value.toString());
        }
    }
}
