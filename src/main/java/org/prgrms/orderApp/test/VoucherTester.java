package org.prgrms.orderApp.test;

import org.prgrms.orderApp.config.AppConfiguration;
import org.prgrms.orderApp.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.orderApp.domain.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.UUID;

public class VoucherTester {
    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherId = UUID.randomUUID();

        var VourcherService = applicationContext.getBean(VoucherService.class);
        VourcherService.saveVoucher(new FixedAmountVoucher(voucherId,10L));

    }

}
