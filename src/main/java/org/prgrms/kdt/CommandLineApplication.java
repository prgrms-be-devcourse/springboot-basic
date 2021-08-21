package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.voucher.application.VoucherService;
import org.prgrms.kdt.voucher.presentation.VoucherController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        new VoucherController(voucherService).play();
    }
}
