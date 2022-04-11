package org.prgrms.voucherprgrms.io;

import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

@Component
public class Console implements OutputConsole, InputConsole {
    private static final Logger logger = LoggerFactory.getLogger(Console.class);
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String commandInput() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type **exit** to exit the program.");
        System.out.println("Type **create** to create a new voucher.");
        System.out.println("Type **list** to list all vouchers.");
        System.out.println();

        return scanner.nextLine();
    }

    @Override
    public long getVoucherValue(String message) {
        System.out.println(message);
        System.out.println();

        return Long.valueOf(scanner.nextLine());
    }

    @Override
    public String getVoucherType() {
        System.out.println("Select **VoucherType**");
        for (VoucherType type : VoucherType.values()) {
            System.out.println(MessageFormat.format("- {0}", type.getName()));
        }
        System.out.println();

        return scanner.nextLine();
    }

    @Override
    public void voucherList(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            //UUID, VoucherTypeName
            System.out.println(MessageFormat.format("Voucher {0} is {1}", voucher.getVoucherId(), voucher.toString()));
        }
        System.out.println();
    }

    @Override
    public void commandErrorMessage() {
        logger.info("COMMAND ERROR");
        System.out.println("잘못된 입력입니다.");
    }
}
