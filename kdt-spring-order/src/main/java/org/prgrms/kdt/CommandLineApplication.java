package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.prgrms.kdt.voucher.io.Console;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommandLineApplication {
    public static void main(String[] args) {
        // app context에 bean 등록
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        VoucherController controller = new VoucherController(applicationContext.getBean(VoucherService.class));
        controller.run();
        applicationContext.close();
    }
}
