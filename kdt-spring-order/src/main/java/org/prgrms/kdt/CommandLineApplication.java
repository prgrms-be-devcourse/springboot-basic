package org.prgrms.kdt;

import org.prgrms.kdt.application.Console;
import org.prgrms.kdt.application.voucher.VoucherApplication;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.prgrms.kdt.infrastructure.configuration.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        new VoucherApplication(voucherService, new Console(), new Console()).run();
    }
}
