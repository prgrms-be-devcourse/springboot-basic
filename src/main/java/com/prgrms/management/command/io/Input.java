package com.prgrms.management.command.io;

import com.prgrms.management.config.GuideType;
import com.prgrms.management.voucher.domain.VoucherRequest;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine() {
        return scanner.nextLine();
    }

    public VoucherRequest inputVoucherType() {
        System.out.println(GuideType.VOUCHER.getMESSAGE());
        String voucherType = scanner.nextLine();
        System.out.println(GuideType.DISCOUNT.getMESSAGE());
        String amount = scanner.nextLine();
        return new VoucherRequest(voucherType,amount);
    }
}
