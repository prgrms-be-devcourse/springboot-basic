package org.prgrms.kdt;

import org.prgrms.kdt.domain.voucher.application.VoucherCommandLineApplication;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.prgrms.kdt.infrastructure.configuration.AppConfiguration;
import org.prgrms.kdt.infrastructure.voucher.io.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        new VoucherCommandLineApplication(voucherService, new Console(), new Console()).run();
    }
}
