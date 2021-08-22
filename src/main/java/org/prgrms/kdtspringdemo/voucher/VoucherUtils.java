package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherUtils {
    private final VoucherService voucherService;
    public VoucherUtils() {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = application.getBean(VoucherService.class);
    }

    public void createVoucher(String[] splitList) {
        Assert.isTrue(splitList.length == 2, "This command did not receive the required arguments.");
        try {
            String voucherName = splitList[0];
            Long data = Long.parseLong(splitList[1]);
            if (voucherName.equals("F")) {
                Voucher voucher = voucherService.createFixedAmountVoucher(UUID.randomUUID(), data);
                System.out.println("This is create : " + voucher);
            } else if (voucherName.equals("P")) {
                Voucher voucher = voucherService.createPercentDiscountVoucher(UUID.randomUUID(), data);
                System.out.println("This is create : " + voucher);
            } else {
                System.out.println("None Voucher!!! : " + voucherName);
            }
        } catch (Exception e) {
            System.out.println("[ERROR]Invalid create command");
            System.out.println(MessageFormat.format("Your input : {0}, {1}", splitList[0], splitList[1]));
        }

    }

    public void printAll() {
        voucherService.printAllVoucher();
    }
}
