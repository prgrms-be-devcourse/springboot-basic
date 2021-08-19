package org.prgrms.orderApp.test;

import org.prgrms.orderApp.AppConfiguration;
import org.prgrms.orderApp.model.voucher.FixedAmountVoucher;
import org.prgrms.orderApp.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

public class VoucherTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherId = UUID.randomUUID();

        var VourcherService = applicationContext.getBean(VoucherService.class);
        VourcherService.saveVoucher(new FixedAmountVoucher(voucherId,10L));

    }

}
