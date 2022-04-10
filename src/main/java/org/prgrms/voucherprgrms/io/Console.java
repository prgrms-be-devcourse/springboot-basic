package org.prgrms.voucherprgrms.io;

import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements OutputConsole, InputConsole {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String commandInput() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");

        return scanner.nextLine();
    }

    @Override
    public long getVoucherValue(String message) {
        System.out.println(message);
        return Long.valueOf(scanner.nextLine());
    }

    @Override
    public void voucherList(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            System.out.println(MessageFormat.format("Voucher {0} is {1}", voucher.getVoucherId(), voucher.toString()));
            //UUID, VoucherTypeName
        }
    }
}
