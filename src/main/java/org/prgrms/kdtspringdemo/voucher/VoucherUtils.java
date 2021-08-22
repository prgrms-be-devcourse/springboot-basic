package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.UUID;

public class VoucherUtils {
    private final VoucherService voucherService;
    public VoucherUtils() {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = application.getBean(VoucherService.class);
    }

    public void createVoucher(String[] splitList) {
        Assert.isTrue(splitList.length == 2, "This command did not receive the required arguments.");
        String voucherName = splitList[0];
        if (voucherName.equals("F")) {
            Voucher voucher = voucherService.createFixedAmountVoucher(UUID.randomUUID(), Long.parseLong(splitList[1]));
            System.out.println("This is create : " + voucher);
        } else if (voucherName.equals("P")) {
            Voucher voucher = voucherService.createPercentDiscountVoucher(UUID.randomUUID(), Long.parseLong(splitList[1]));
            System.out.println("This is create : " + voucher);
        } else {
            System.out.println("None Voucher!!! : " + voucherName);
        }
    }

    public void printAll() {
        voucherService.printAllVoucher();
    }
}
